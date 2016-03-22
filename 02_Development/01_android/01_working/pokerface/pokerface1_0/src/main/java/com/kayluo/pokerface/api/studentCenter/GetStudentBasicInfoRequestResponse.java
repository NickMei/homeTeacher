package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 9/6/2015.
 */
public class GetStudentBasicInfoRequestResponse extends RequestResponseBase {
    public UserBasicInfo basicInfo;
    public GetStudentBasicInfoRequestResponse(ResponseListener responselistener) {
        super(responselistener);
        this.url = domain +"studentcenter/getStudentBasicInfo";
        Type jsonType = new TypeToken<ResponseInfo<UserBasicInfo>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    basicInfo = (UserBasicInfo) responseInfo.response;
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
