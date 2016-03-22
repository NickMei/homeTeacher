package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 9/6/2015.
 */
public class GetStudentCourseRecordRequestResponse extends RequestResponseBase {
    public GetStudentCourseRecordRequestResponse(ResponseListener responselistener) {
        super(responselistener);

        this.url = domain +"studentcenter/getStudentCourseRecord";
        Type jsonType = new TypeToken<ResponseInfo<GetStudentCourseRecordRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    GetStudentCourseRecordRequestResponse mResponse = (GetStudentCourseRecordRequestResponse) responseInfo.response;
                    total_class_time = mResponse.total_class_time;
                    total_comment = mResponse.total_comment;
                    total_tutor = mResponse.total_tutor;
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

    public String total_class_time;
    public String total_tutor;
    public String total_comment;

}
