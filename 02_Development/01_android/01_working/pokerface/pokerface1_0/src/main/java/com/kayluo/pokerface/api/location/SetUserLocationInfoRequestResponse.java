package com.kayluo.pokerface.api.location;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by Nick on 2016-04-20.
 */
public class SetUserLocationInfoRequestResponse extends RequestResponseBase {

    public SetUserLocationInfoRequestResponse(String cityId,ResponseListener responseListener) {
        super(responseListener);

        String url = domain + "location/resetCity";

        requestJsonMap.put("city_id", cityId);
        Type jsonType = new TypeToken<ResponseInfo<SetUserLocationInfoRequestResponse>>() {
        }.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
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
