package com.kayluo.pokerface.api.location;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.District;
import com.kayluo.pokerface.dataModel.Province;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/8/26.
 */
public class GetDistrictListRequestResponse extends RequestResponseBase {
    public List<District> districtList;
    public GetDistrictListRequestResponse(String cityID,ResponseListener responseListener) {
        super(responseListener);
        districtList = new ArrayList<District>();
        this.url = this.domain + "location/getDistrictList?cityID=" + cityID;

        Type jsonType = new TypeToken<ResponseInfo<GetDistrictListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    GetDistrictListRequestResponse mResponse = (GetDistrictListRequestResponse) responseInfo.response;
                    districtList = mResponse.districtList;
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
