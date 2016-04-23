package com.kayluo.pokerface.api.auth;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jiweili on 27/9/2015.
 */
public class SendVerifyMessageRequestResponse extends RequestResponseBase {
    @SerializedName(value = "msgcode")
    public String messageCode;
    public SendVerifyMessageRequestResponse(Boolean newAccount, String mobile, ResponseListener responseListener) {
        super(responseListener);
        requestJsonMap.put("target", newAccount ? "register" : "change_mobile");
        requestJsonMap.put("mobile", mobile);
        this.url = domain +"auth/sendMsg";
        Type jsonType = new TypeToken<ResponseInfo<SendVerifyMessageRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    SendVerifyMessageRequestResponse mResponse = (SendVerifyMessageRequestResponse) responseInfo.response;
                    messageCode = mResponse.messageCode;
                }
                listener.onCompleted(responseInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onVolleyError(error);
            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);
    }
}
