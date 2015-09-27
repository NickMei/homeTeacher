package com.kayluo.pokerface.core;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class UserConfig {
    public static final String PREFS_NAME = "MyPrefsFile";
    public String userId = "0";
    public String token = "0";
    public String head_photo = "";
    public boolean isSignedIn = false;

    public void saveToStorage(Context context) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId",userId);
        editor.putString("token",token);
        editor.putBoolean("isSignedIn",isSignedIn);
        editor.commit();
    }

    public UserConfig(Context context){
        super();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        userId = settings.getString("userId", "0");
        token = settings.getString("token", "0");
        isSignedIn = settings.getBoolean("isSignedIn",false);
    }

    public void logout(Context context)
    {
        userId = "0";
        token = "0";
        head_photo = "";
        isSignedIn = false;
        this.saveToStorage(context);
    }

    
}
