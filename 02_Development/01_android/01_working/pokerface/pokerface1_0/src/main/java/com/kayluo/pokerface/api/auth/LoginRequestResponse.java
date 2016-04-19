package com.kayluo.pokerface.api.auth;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.util.MD5Hash;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 2015/8/30.
 */
public class LoginRequestResponse extends RequestResponseBase {

    public String head_photo;
    public String mobile;
    public String name;
    public String token;
    public String user_id;
    public String token_timestamp;

    public LoginRequestResponse(String mobile, String password, ResponseListener responseListener)
    {
        super(responseListener);

        String url = domain +"auth/login";

        requestJsonMap.put("mobile",mobile);
        requestJsonMap.put("password",MD5Hash.md5(password));

        Type jsonType = new TypeToken<ResponseInfo<LoginRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType,requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    LoginRequestResponse authResponse = (LoginRequestResponse) responseInfo.response;
                    head_photo = authResponse.head_photo;
                    token = authResponse.token;
                    user_id = authResponse.user_id;
                    LoginRequestResponse.this.mobile = authResponse.mobile;
                    name = authResponse.name;
                }
                listener.onCompleted(responseInfo);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);

    }
    private class AccountInfo {
        private String mobile;
        private String password;
    }
}
