package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by cxm170 on 2015/9/9.
 */
public class SaveStudentBasicInfoRequestResponse extends RequestResponseBase {
    public SaveStudentBasicInfoRequestResponse(UserBasicInfo basicInfo,ResponseListener responseListener) {
        super(responseListener);
        this.url = domain +"studentcenter/saveStudentBasicInfo";
        Type jsonType = new TypeToken<ResponseInfo<Object>>() {}.getType();
        String jsonString = gson.toJson(basicInfo);
        requestJsonMap = (Map<String, Object>) gson.fromJson(jsonString, requestJsonMap.getClass());
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                listener.onCompleted((ResponseInfo)response);
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
