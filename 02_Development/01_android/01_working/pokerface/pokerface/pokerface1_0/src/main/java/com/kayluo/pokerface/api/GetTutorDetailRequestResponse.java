package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorDetail;

import org.json.JSONObject;

/**
 * Created by cxm170 on 2015/8/9.
 */
public class GetTutorDetailRequestResponse extends RequestResponseBase  {
    private String tutorId;
    TutorDetail tutorDetail;

    public GetTutorDetailRequestResponse(String tutorId, final ResponseListener responseListener)
    {
        super(responseListener);
        this.tutorId = tutorId;

        String url = domain + "tutorinfo/getTutorDetailInfo?tutor_id=" + tutorId;

        JSONObject responseObject = new JSONObject();
        StringRequest request = new StringRequest(url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                tutorDetail = gson.fromJson((String) response, TutorDetail.class);
                listener.onCompleted(tutorDetail);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppManager.shareInstance().addToRequestQueue(request);

    }
}
