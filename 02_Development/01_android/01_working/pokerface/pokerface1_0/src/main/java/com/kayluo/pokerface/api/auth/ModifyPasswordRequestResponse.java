package com.kayluo.pokerface.api.auth;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.util.MD5Hash;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 2015/9/27.
 */
public class ModifyPasswordRequestResponse extends RequestResponseBase {
    public String token;
    public String token_timestamp;
    public ModifyPasswordRequestResponse(String oldPassword, String newPassword, ResponseListener responselistener) {
        super(responselistener);
        this.requestJsonMap.put("target", "change_password");
        this.requestJsonMap.put("old_password", MD5Hash.md5(oldPassword));
        this.requestJsonMap.put("new_password", MD5Hash.md5(newPassword));
        this.url = domain +"auth/modifyPassword";
        Type jsonType = new TypeToken<ResponseInfo<ModifyPasswordRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, this.requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    ModifyPasswordRequestResponse mResponse = (ModifyPasswordRequestResponse) responseInfo.response;
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
