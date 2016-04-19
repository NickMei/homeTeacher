package com.kayluo.pokerface.api.auth;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 2015/9/13.
 */
public class LogoutRequestResponse extends RequestResponseBase {

    public LogoutRequestResponse(ResponseListener listener) {
        super(listener);
        this.url = domain +"auth/logout";
        Type jsonType = new TypeToken<ResponseInfo<Object>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Object object = response;
                LogoutRequestResponse.this.listener.onCompleted(new ResponseInfo());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);
    }
}
