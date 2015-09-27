package com.kayluo.pokerface.core;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class SettingManager {

    public AppConfig getAppConfig() {
        return appConfig;
    }

    private  AppConfig appConfig;

    public UserConfig getUserConfig() {
        return userConfig;
    }

    private UserConfig userConfig;

    public SettingManager(AppConfig appConfig,UserConfig userConfig)
    {
            this.appConfig = appConfig;
            this.userConfig = userConfig;
    }
}
