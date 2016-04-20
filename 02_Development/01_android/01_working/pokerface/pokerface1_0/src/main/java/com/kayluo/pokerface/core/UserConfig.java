package com.kayluo.pokerface.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.kayluo.pokerface.api.auth.LoginRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.location.GetUserLocationInfoRequestResponse;
import com.kayluo.pokerface.api.studentCenter.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.dataModel.ResponseInfo;
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
    private static String PREFS_NAME = "MyPrefsFile";
    public String userId;
    public UserProfile profile;
    public boolean isSignedIn;
    private SharedPreferences settings;
    GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;
    GetUserLocationInfoRequestResponse getUserLocationInfoRequestResponse;

    public void saveToStorage() {
//        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId", userId);
        editor.putBoolean("isSignedIn", isSignedIn);
        editor.commit();
        // insert Database
        profile.insertOrUpdate(userId);

    }

    public UserConfig(Context context){
        super();
        this.settings = context.getSharedPreferences(PREFS_NAME, 0);
        userId = settings.getString("userId", "0");
        isSignedIn = settings.getBoolean("isSignedIn", false);
        // query Database
        profile = new UserProfile(context,userId);
    }

    public void logout(Context context)
    {
        userId = "0";
        isSignedIn = false;

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId", userId);
        editor.putBoolean("isSignedIn", isSignedIn);
        editor.commit();
    }

    public void login(LoginRequestResponse loginRequestResponse, Context context)
    {

        isSignedIn = true;
        userId = loginRequestResponse.user_id;
        profile = new UserProfile(context,userId);
        profile.mobile = loginRequestResponse.mobile;
        profile.token = loginRequestResponse.token;

        getStudentBasicInfoRequestResponse = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo responseInfo) {
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    UserProfile userProfile = AppManager.shareInstance().settingManager.getUserConfig().profile;
                    userProfile.name = getStudentBasicInfoRequestResponse.basicInfo.name;
                    userProfile.head_photo = getStudentBasicInfoRequestResponse.basicInfo.head_photo;
                }

            }

        });

        getUserLocationInfoRequestResponse = new GetUserLocationInfoRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == EReturnCode.SUCCESS.getValue())
                {
                    UserProfile userProfile = AppManager.shareInstance().settingManager.getUserConfig().profile;
                    userProfile.city.cityID = getUserLocationInfoRequestResponse.cityId;
                    userProfile.city.cityName = getUserLocationInfoRequestResponse.cityName;
                }
            }
        });

        saveToStorage();

    }

    
}
