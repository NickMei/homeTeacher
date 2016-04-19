package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jiweili on 27/9/2015.
 */
public class SendVerifyMessageRequestResponse extends RequestResponseBase {
    public String msgcode;
    public SendVerifyMessageRequestResponse(Boolean newAccount, String mobile, ResponseListener responseListener) {
        super(responseListener);
        Map<String,String> hashedMap = new LinkedHashMap<String,String>();
        hashedMap.put("target", newAccount ? "register" : "change_mobile");
        hashedMap.put("mobile", mobile);

        this.url = domain +"auth/sendMsg";
        Type jsonType = new TypeToken<ResponseInfo<SendVerifyMessageRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, hashedMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    SendVerifyMessageRequestResponse mResponse = (SendVerifyMessageRequestResponse) responseInfo.response;
                    msgcode = mResponse.msgcode;
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
