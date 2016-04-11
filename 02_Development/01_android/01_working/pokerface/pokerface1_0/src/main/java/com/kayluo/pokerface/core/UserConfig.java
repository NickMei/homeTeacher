package com.kayluo.pokerface.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.dataModel.UserBasicInfo;
import com.kayluo.pokerface.database.UserProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class UserConfig {
    public static String PREFS_NAME = "MyPrefsFile";
    public String userId = "0";
    public UserProfile profile ;
    public boolean isSignedIn = false;

    public void saveToStorage(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId", userId);
        editor.putBoolean("isSignedIn", isSignedIn);
        editor.commit();
        // insert Database
        profile.insertOrUpdate(userId);

    }

    public UserConfig(Context context){
        super();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        userId = settings.getString("userId", "0");
        isSignedIn = settings.getBoolean("isSignedIn", false);
        // query Database
        profile = new UserProfile(context,userId);
    }

    public void logout(Context context)
    {
        userId = "0";
        isSignedIn = false;
        this.saveToStorage(context);
    }

    
}
