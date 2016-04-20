package com.kayluo.pokerface.core;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class SettingManager {

    private UserConfig userConfig;
    private  AppConfig appConfig;

    public AppConfig getAppConfig()
    {
        return appConfig;
    }

    public UserConfig getUserConfig()
    {
        return userConfig;
    }

    public SettingManager(Context context)
    {
            this.appConfig = new AppConfig();
            this.userConfig = new UserConfig(context);
    }
}
