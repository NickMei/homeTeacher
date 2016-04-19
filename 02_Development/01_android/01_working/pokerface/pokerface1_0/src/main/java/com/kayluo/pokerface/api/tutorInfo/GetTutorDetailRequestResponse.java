package com.kayluo.pokerface.api.tutorInfo;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorDetail;

import java.lang.reflect.Type;

/**
 * Created by cxm170 on 2015/8/9.
 */
public class GetTutorDetailRequestResponse extends RequestResponseBase {
    public TutorDetail tutorDetail;

    public GetTutorDetailRequestResponse(String tutorId, final ResponseListener responseListener)
    {
        super(responseListener);

        String url = domain + "tutorinfo/getTutorDetailInfo";

        requestJsonMap.put("tutor_id",tutorId);
        requestJsonMap.put("is_app_query",1);

        Type jsonType = new TypeToken<ResponseInfo<TutorDetail>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    tutorDetail  = (TutorDetail) responseInfo.response;
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
