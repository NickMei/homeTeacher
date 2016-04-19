package com.kayluo.pokerface.api.order;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TeachInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorBasicInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

import java.lang.reflect.Type;

/**
 * Created by Nick on 2016-04-12.
 */
public class GetOrderTutorInfoRequestResponse extends RequestResponseBase {

    @SerializedName(value = "student_info")
    public UserBasicInfo studentBasicInfo;
    @SerializedName(value = "tutor_info")
    public TutorBasicInfo tutorBasicInfo;
    @SerializedName(value = "teach_info")
    public TeachInfo teachInfo;

    public GetOrderTutorInfoRequestResponse(String tutorID,ResponseListener responseListener) {
        super(responseListener);
        this.url = domain +"order/getOrderTutorInfo";
        requestJsonMap.put("tutor_id",tutorID);
        Type jsonType = new TypeToken<ResponseInfo<GetOrderTutorInfoRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetOrderTutorInfoRequestResponse mResponse = (GetOrderTutorInfoRequestResponse) responseInfo.response;
                    studentBasicInfo = mResponse.studentBasicInfo;
                    tutorBasicInfo = mResponse.tutorBasicInfo;
                    teachInfo = mResponse.teachInfo;
                }
                listener.onCompleted(responseInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ResponseInfo responseInfo = new ResponseInfo();
                responseInfo.returnCode = EReturnCode.UNKNOWN_ERROR.getValue();
                listener.onCompleted(responseInfo);
            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);
    }
}
