package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.Util.MD5Hash;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/9/27.
 */
public class ChangePasswordRequestResponse extends RequestResponseBase {
    private String token;
    private String token_timestamp;
    public ChangePasswordRequestResponse(String oldPassword, String newPassword,ResponseListener listener) {
        super(listener);
        Map<String,String> hashedMap = new LinkedHashMap<String,String>();
        hashedMap.put("old_password", MD5Hash.md5(oldPassword));
        hashedMap.put("new_password", MD5Hash.md5(newPassword));
        this.jsonString = gson.toJson(hashedMap);
        this.url = domain +"studentcenter/getStudentOrderList";
        Type jsonType = new TypeToken<ResponseInfo<ChangePasswordRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, jsonString, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode.equals("0")) {
                    MyResponse mResponse = (MyResponse) responseInfo.response;
                    list = mResponse.list;
                }
                listener.onCompleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, tag_json_obj);
    }
}
