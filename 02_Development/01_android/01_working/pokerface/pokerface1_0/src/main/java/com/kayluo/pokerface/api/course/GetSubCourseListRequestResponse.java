package com.kayluo.pokerface.api.course;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.SubCourse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class GetSubCourseListRequestResponse extends RequestResponseBase {

    @SerializedName(value = "sub_course_list")
    public List<SubCourse> subCourseList;
    public GetSubCourseListRequestResponse(String courseId, ResponseListener responseListener)
    {
        super(responseListener);
        String url = domain +"course/getSubCourseList";
        requestJsonMap.put("course_id",courseId);
        Type jsonType = new TypeToken<ResponseInfo<GetSubCourseListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetSubCourseListRequestResponse mResponse = (GetSubCourseListRequestResponse) responseInfo.response;
                    subCourseList = mResponse.subCourseList;
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
