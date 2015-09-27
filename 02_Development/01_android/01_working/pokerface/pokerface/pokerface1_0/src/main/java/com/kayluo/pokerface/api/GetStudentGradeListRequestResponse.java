package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
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
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, jsonString, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode.equals("0")) {
                    GetStudentGradeListRequestResponse mResponse = (GetStudentGradeListRequestResponse) responseInfo.response;
                    grade_list = mResponse.grade_list;
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
