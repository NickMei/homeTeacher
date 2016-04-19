package com.kayluo.pokerface.api.course;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.Stage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/13.
 */
public class GetCourseListRequestResponse extends RequestResponseBase {
    @SerializedName(value = "course_list")
    public List<Course> courseList;
    public GetCourseListRequestResponse(Stage stage,ResponseListener responseListener)
    {
        super(responseListener);

        String url = domain + "course/getCourseList";

        requestJsonMap.put("stage_id",stage.stageId);

        Type jsonType = new TypeToken<ResponseInfo<GetCourseListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetCourseListRequestResponse mResponse = (GetCourseListRequestResponse) responseInfo.response;
                    courseList = mResponse.courseList;
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
