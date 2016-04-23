package com.kayluo.pokerface.api.base;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class RequestResponseBase {
    protected String domain = "http://112.74.81.48/zhihuieducation/";
    protected static Gson gson = new Gson();
    protected Map<String,String> headers = new HashMap<>();
    protected String url;
    protected Map<String,Object> requestJsonMap = new HashMap<String,Object>();
    protected ResponseListener listener;

    public RequestResponseBase(ResponseListener responseListener) {
        this.listener = responseListener;
    }

    public void cancelPendingRequest()
    {
        AppManager.shareInstance().cancelPendingRequests(this.url);
    }

    public interface ResponseListener {
       void onCompleted(ResponseInfo response);

    }

    protected void onVolleyError(VolleyError error)
    {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.returnCode = EReturnCode.UNKNOWN_ERROR.getValue();
        listener.onCompleted(responseInfo);
    }

}
