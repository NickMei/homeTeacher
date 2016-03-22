package com.kayluo.pokerface.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nick on 2016-03-07.
 */
public class UserProfileDBHelper extends SQLiteOpenHelper
{

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.UserEntity.TABLE_NAME + " (" +
                    UserProfile.UserEntity._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.UserEntity.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                    UserProfile.UserEntity.COLUMN_NAME_SEARCH_HISTORY + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.UserEntity.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserProfile.db";

    public UserProfileDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
//        onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
//        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
