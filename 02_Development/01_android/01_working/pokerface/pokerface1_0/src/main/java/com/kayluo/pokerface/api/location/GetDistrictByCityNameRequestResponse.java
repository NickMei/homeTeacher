package com.kayluo.pokerface.api.location;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.District;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by cxm170 on 2015/10/27.
 */
public class GetDistrictByCityNameRequestResponse extends RequestResponseBase {
    @SerializedName(value = "district_list")
    public List<District> districtList;
    public GetDistrictByCityNameRequestResponse(String city,ResponseListener responseListener) {
        super(responseListener);

        requestJsonMap.put("city_name",city + "市");
        String url = domain +"location/getDistrictByCityName";
        Type jsonType = new TypeToken<ResponseInfo<GetDistrictByCityNameRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetDistrictByCityNameRequestResponse mResponse = (GetDistrictByCityNameRequestResponse) responseInfo.response;
                    districtList = mResponse.districtList;
                }
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
