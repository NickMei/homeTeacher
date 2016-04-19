package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.Stage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Mei Youzhi on 12/14/2015.
 */
public class GetAllCourseListRequestResponse extends RequestResponseBase {

    public List<Stage>  stageList;
    public GetAllCourseListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);

        String url = domain + "course/getAllCourseList";

        Type jsonType = new TypeToken<ResponseInfo<List<Stage>>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    stageList = (List<Stage>) responseInfo.response;
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
