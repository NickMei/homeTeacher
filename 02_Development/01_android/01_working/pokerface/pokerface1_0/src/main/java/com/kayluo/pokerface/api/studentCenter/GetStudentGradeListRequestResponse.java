package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/9/17.
 */
public class GetStudentGradeListRequestResponse extends RequestResponseBase {
    public ArrayList<String> grade_list;
    public GetStudentGradeListRequestResponse(ResponseListener responselistener) {
        super(responselistener);
        this.url = domain +"studentcenter/getStudentGradeList";
        Type jsonType = new TypeToken<ResponseInfo<GetStudentGradeListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetStudentGradeListRequestResponse mResponse = (GetStudentGradeListRequestResponse) responseInfo.response;
                    grade_list = mResponse.grade_list;
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
