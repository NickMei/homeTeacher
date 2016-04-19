package com.kayluo.pokerface.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.dataModel.City;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2016-03-07.
 */
public final class UserProfile {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public String token;
    public String head_photo;
    public String mobile;
    public City city;
    public String name;
    public List<String> searchHistoryList;
    private UserProfileDBHelper mDbHelper;

    public UserProfile(Context context,String userID)
    {
        token = "0";
        head_photo = "";
        mobile = "";
        city = new City();
        name = "";
        searchHistoryList =  new ArrayList<String>();
        mDbHelper = new UserProfileDBHelper(context);
        this.queryUserProfile(userID);
    }

    /* Inner class that defines the table contents */
    public static abstract class UserEntity implements BaseColumns {
        public static final String TABLE_NAME = "userEntity";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_TOKEN = "token";
        public static final String COLUMN_NAME_MOBILE = "mobile";
        public static final String COLUMN_NAME_LOCATION_CITY = "location";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_SEARCH_HISTORY = "searchHistory";
    }

    public void insertOrUpdate(String userId)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Gson gson = new Gson();
        String searchHistoryJsonString = gson.toJson(searchHistoryList);
        String cityJsonString = gson.toJson(city);
        // Create a new map of values, where column names are the keys


        if ((userId) == null)
        {

            ContentValues values = new ContentValues();
            values.put(UserEntity.COLUMN_NAME_USER_ID, userId);
            values.put(UserEntity.COLUMN_NAME_TOKEN, token);
            values.put(UserEntity.COLUMN_NAME_MOBILE, mobile);
            values.put(UserEntity.COLUMN_NAME_LOCATION_CITY, cityJsonString);
            values.put(UserEntity.COLUMN_NAME_USER_NAME, name);
            values.put(UserEntity.COLUMN_NAME_SEARCH_HISTORY, searchHistoryJsonString);

            // Insert the new row, returning the primary key value of the new row
             db.insert(
                    UserEntity.TABLE_NAME,
                    UserEntity.COLUMN_NAME_SEARCH_HISTORY,
                    values);
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(UserEntity.COLUMN_NAME_USER_ID, userId);
            values.put(UserEntity.COLUMN_NAME_TOKEN, token);
            values.put(UserEntity.COLUMN_NAME_MOBILE, mobile);
            values.put(UserEntity.COLUMN_NAME_LOCATION_CITY, cityJsonString);
            values.put(UserEntity.COLUMN_NAME_USER_NAME, name);
            values.put(UserEntity.COLUMN_NAME_SEARCH_HISTORY, searchHistoryJsonString);
            String strFilter = UserEntity.COLUMN_NAME_USER_ID + "=" + userId ;
            db.update(UserEntity.TABLE_NAME,
                    values,
                    strFilter,
                    null);
        }

    }

    private void queryUserProfile(String userID)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<String> searchHistoryList = null;
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntity.COLUMN_NAME_TOKEN,
                UserEntity.COLUMN_NAME_MOBILE,
                UserEntity.COLUMN_NAME_LOCATION_CITY,
                UserEntity.COLUMN_NAME_USER_NAME,
                UserEntity.COLUMN_NAME_SEARCH_HISTORY,
        };

        // Define 'where' part of query.
        String selection =  UserEntity.COLUMN_NAME_USER_ID + "=?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(userID) };

        // How you want the results sorted in the resulting Cursor

        // Issue SQL statement.
        Cursor cursor = db.query(
                UserEntity.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if (cursor != null)
        {
            if(cursor.getCount() <= 0)
            {
                cursor.close();
                return;
            }

            cursor.moveToFirst();
            this.token = cursor.getString(cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_TOKEN));
            this.mobile = cursor.getString(cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_MOBILE));
            this.name = cursor.getString(cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_USER_NAME));
            String searchHistory = cursor.getString(cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_SEARCH_HISTORY));
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            this.searchHistoryList = new Gson().fromJson(searchHistory,listType);
            String locationJsobString  = cursor.getString(cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_LOCATION_CITY));
            this.city = new Gson().fromJson(locationJsobString,City.class);
            cursor.close();

        }

    }

}
