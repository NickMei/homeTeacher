package com.kayluo.pokerface.api;

import com.google.gson.Gson;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class RequestResponseBase {
    protected static String domain = "http://112.74.81.48/zhihuieducation/";
    protected static Gson gson = new Gson();
    protected static Map<String,String> headers = new HashMap<>();

    protected String url;
    protected Map<String,Object> requestJsonMap = new HashMap<String,Object>();

    protected ResponseListener listener;

    public RequestResponseBase(ResponseListener listener) {

        this.listener = listener;
    }

    public void cancelPendingRequest()
    {
        AppManager.shareInstance().cancelPendingRequests(this.url);
    }

    public static interface ResponseListener {
        public abstract void onCompleted(ResponseInfo response);

    }

}
