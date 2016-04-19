package com.kayluo.pokerface.api.auth;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.util.MD5Hash;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 2015/10/12.
 */
public class RegisterAccountRequestResponse extends RequestResponseBase {
    public String token;
    public String token_timestamp;
    public RegisterAccountRequestResponse(String token, String password, ResponseListener responseListener) {
        super(responseListener);
        UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
        requestJsonMap.put("password", MD5Hash.md5(password));
        requestJsonMap.put("identity", "student");
        requestJsonMap.put("mobile", userConfig.profile.mobile);
        requestJsonMap.put("token", token);
        this.url = domain +"auth/registerAccount";
        Type jsonType = new TypeToken<ResponseInfo<ReAuthRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    AuthMsgCodeRequestResponse mResponse = (AuthMsgCodeRequestResponse) responseInfo.response;
                    RegisterAccountRequestResponse.this.token = mResponse.token;
                    token_timestamp = mResponse.token_timestamp;
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
}
