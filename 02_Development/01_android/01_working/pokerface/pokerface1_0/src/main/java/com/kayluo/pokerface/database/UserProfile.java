package com.kayluo.pokerface.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2016-03-07.
 */
public final class UserProfile {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserProfileDBHelper mDbHelper;
    public UserProfile(Context context)
    {
        mDbHelper = new UserProfileDBHelper(context);
    }

    /* Inner class that defines the table contents */
    public static abstract class UserEntity implements BaseColumns {
        public static final String TABLE_NAME = "userEntity";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_SEARCH_HISTORY = "searchHistory";
    }

    public void insertOrUpdate(String userId, List<String> searchHistoryList)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String searchHistory = new Gson().toJson(searchHistoryList);
        // Create a new map of values, where column names are the keys


        if (querySearchHistory(userId) == null)
        {

            ContentValues values = new ContentValues();
            values.put(UserEntity.COLUMN_NAME_USER_ID, userId);
            values.put(UserEntity.COLUMN_NAME_SEARCH_HISTORY, searchHistory);
            // Insert the new row, returning the primary key value of the new row
             db.insert(
                    UserEntity.TABLE_NAME,
                    UserEntity.COLUMN_NAME_SEARCH_HISTORY,
                    values);
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(UserEntity.COLUMN_NAME_SEARCH_HISTORY, searchHistory);
            String strFilter = UserEntity.COLUMN_NAME_USER_ID + "=" + userId ;
            db.update(UserEntity.TABLE_NAME,
                    values,
                    strFilter,
                    null);
        }


    }

    public List<String> querySearchHistory(String userID)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<String> searchHistoryList = null;
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
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
                return searchHistoryList;
            }

            cursor.moveToFirst();
            String searchHistory = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserEntity.COLUMN_NAME_SEARCH_HISTORY)
            );
            cursor.close();
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            searchHistoryList = new Gson().fromJson(searchHistory,listType);
        }
        return searchHistoryList;

    }

}
