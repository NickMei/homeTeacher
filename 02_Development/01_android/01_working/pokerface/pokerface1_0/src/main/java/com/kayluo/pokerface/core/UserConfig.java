package com.kayluo.pokerface.core;

import android.content.Context;
import android.content.SharedPreferences;

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
    public String token = "0";
    public String head_photo = "";
    public String mobile = "";
    public boolean isSignedIn = false;
    public String city = "";
    public String name = "";
    public List<String> searchHistory;
    private UserProfile userProfile ;

    public void saveToStorage(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId", userId);
        editor.putString("token", token);
        editor.putString("mobile", mobile);
        editor.putBoolean("isSignedIn", isSignedIn);
        editor.putString("city", city);
        editor.putString("name", name);
        editor.commit();
        // insert Database
        userProfile.insertOrUpdate(userId,searchHistory);

    }

    public UserConfig(Context context){
        super();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        userId = settings.getString("userId", "0");
        token = settings.getString("token", "0");
        mobile = settings.getString("mobile","");
        city = settings.getString("city","");
        name = settings.getString("name", "");
        isSignedIn = settings.getBoolean("isSignedIn", false);

        // query Database
        userProfile = new UserProfile(context);
        searchHistory = userProfile.querySearchHistory(userId);
    }

    public void logout(Context context)
    {
        userId = "0";
        token = "0";
        head_photo = "";
        mobile = "";
        isSignedIn = false;
        city = "";
        name = "";
        searchHistory = new ArrayList<String>();
        this.saveToStorage(context);
    }

    
}
