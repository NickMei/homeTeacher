package com.kayluo.pokerface.api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kayluo.pokerface.Util.MD5Hash;
import com.kayluo.pokerface.core.AppManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class RequestResponseBase {
    protected static String domain = "http://112.74.81.48/zhihuieducation/";
    protected static String tag_json_obj = "json_obj_req";
    protected static Gson gson;

    protected String url;
    protected String jsonString = "{}";

    protected ResponseListener listener;

    public RequestResponseBase(ResponseListener listener) {
        if (gson == null) {
            gson = new Gson();
        }
        this.listener = listener;
    }

    public static interface ResponseListener {
        public abstract void onCompleted(Object response);

    }

}
