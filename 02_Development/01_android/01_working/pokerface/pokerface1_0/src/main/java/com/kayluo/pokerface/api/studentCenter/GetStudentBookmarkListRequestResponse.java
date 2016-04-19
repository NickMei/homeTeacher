package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/10/17.
 */
public class GetStudentBookmarkListRequestResponse extends RequestResponseBase {
    public ArrayList<TutorEntity> list;
    public GetStudentBookmarkListRequestResponse(ResponseListener responseListener) {
        super(responseListener);
        this.url = domain +"studentcenter/getStudentBookmarkList";
        Type jsonType = new TypeToken<ResponseInfo<GetStudentBookmarkListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetStudentBookmarkListRequestResponse mResponse = (GetStudentBookmarkListRequestResponse) responseInfo.response;
                    list = mResponse.list;
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
