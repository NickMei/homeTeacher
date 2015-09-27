package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by cxm170 on 2015/9/9.
 */
public class SaveStudentBasicInfoRequestResponse extends RequestResponseBase {
    public SaveStudentBasicInfoRequestResponse(UserBasicInfo basicInfo,ResponseListener responseListener) {
        super(responseListener);
        this.url = domain +"studentcenter/saveStudentBasicInfo";
        this.jsonString = gson.toJson(basicInfo);
        Type jsonType = new TypeToken<ResponseInfo<Object>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, jsonString, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
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
