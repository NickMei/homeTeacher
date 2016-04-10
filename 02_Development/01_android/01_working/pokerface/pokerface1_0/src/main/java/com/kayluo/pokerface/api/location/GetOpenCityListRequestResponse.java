package com.kayluo.pokerface.api.location;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/9/20.
 */
public class GetOpenCityListRequestResponse extends RequestResponseBase {
    @SerializedName(value = "city_list")
    public ArrayList<String> cityList;
    public GetOpenCityListRequestResponse(ResponseListener responselistener) {
        super(responselistener);

        this.url = domain +"location/getOpenCityList";
        Type jsonType = new TypeToken<ResponseInfo<GetOpenCityListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    GetOpenCityListRequestResponse mResponse = (GetOpenCityListRequestResponse) responseInfo.response;
                    cityList = mResponse.cityList;
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
