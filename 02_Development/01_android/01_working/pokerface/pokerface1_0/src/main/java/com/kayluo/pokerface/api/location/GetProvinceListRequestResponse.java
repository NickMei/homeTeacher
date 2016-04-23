package com.kayluo.pokerface.api.location;

/**
 * Created by Nick on 2016-04-12.
 */

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.Province;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.List;

public class GetProvinceListRequestResponse extends RequestResponseBase {

    @SerializedName(value = "province_list")
    public List<Province> provinceList;
    public GetProvinceListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);
        this.url = domain +"location/getProvinceList";
        Type jsonType = new TypeToken<ResponseInfo<GetProvinceListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetProvinceListRequestResponse mResponse = (GetProvinceListRequestResponse) responseInfo.response;
                    provinceList = mResponse.provinceList;
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