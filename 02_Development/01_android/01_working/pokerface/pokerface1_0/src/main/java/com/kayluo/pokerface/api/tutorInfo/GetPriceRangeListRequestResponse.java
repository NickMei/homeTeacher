package com.kayluo.pokerface.api.tutorInfo;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.PriceRange;
import com.kayluo.pokerface.dataModel.Province;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2016-01-17.
 */
public class GetPriceRangeListRequestResponse extends RequestResponseBase {
    public ArrayList<PriceRange> priceRangeList;
    public GetPriceRangeListRequestResponse(ResponseListener responselistener) {
        super(responselistener);

        this.url = domain +"tutorinfo/getPriceRangeList";
        Type jsonType = new TypeToken<ResponseInfo<ArrayList<PriceRange>>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    priceRangeList =  (ArrayList<PriceRange>) responseInfo.response;
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

    /**
     * Created by cxm170 on 2015/6/16.
     */
    public static class GetProvinceListRequestResponse extends RequestResponseBase {

        @SerializedName(value = "province_list")
        public List<Province> provinceList;
        public GetProvinceListRequestResponse(ResponseListener responseListener)
        {
            super(responseListener);
            String url = domain +"location/getProvinceList";

            Type jsonType = new TypeToken<ResponseInfo<GetProvinceListRequestResponse>>() {}.getType();
            GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    ResponseInfo responseInfo = (ResponseInfo) response;
                    if (responseInfo.returnCode == 0) {
                        GetProvinceListRequestResponse mResponse = (GetProvinceListRequestResponse) responseInfo.response;
                        provinceList = mResponse.provinceList;
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
}
