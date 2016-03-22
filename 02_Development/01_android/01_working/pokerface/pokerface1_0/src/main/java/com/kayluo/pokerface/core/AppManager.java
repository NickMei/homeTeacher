package com.kayluo.pokerface.core;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kayluo.pokerface.util.LocationService;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class AppManager extends Application {

    public static final String TAG = AppManager.class
            .getSimpleName();
    public SettingManager settingManager;
    private static AppManager appManager;

    // volley SDK
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    // baidu SDK
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate(){
        super.onCreate();
        appManager = this;
        appManager.settingManager = new SettingManager(new AppConfig(),new UserConfig(this.getBaseContext()));

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static synchronized AppManager shareInstance()
    {
        return appManager;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
