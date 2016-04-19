package com.kayluo.pokerface.api.auth;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Created by jiweili on 27/9/2015.
 */
public class AuthMsgCodeRequestResponse extends RequestResponseBase {
    public String token;
    public String token_timestamp;
    public AuthMsgCodeRequestResponse(Boolean newAccount,String mobile, String msgCode, ResponseListener responseListener) {
        super(responseListener);
        this.requestJsonMap.put("mobile", mobile);
        this.requestJsonMap.put("msgcode", msgCode);
        this.requestJsonMap.put("target", newAccount ? "register" : "change_mobile");
        this.url = domain +"auth/authMsgCode";
        Type jsonType = new TypeToken<ResponseInfo<AuthMsgCodeRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, this.requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    AuthMsgCodeRequestResponse mResponse = (AuthMsgCodeRequestResponse) responseInfo.response;
                    token = mResponse.token;
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
