package com.kayluo.pokerface.api.location;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.Province;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.database.UserProfile;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Nick on 2016-04-20.
 */
public class GetUserLocationInfoRequestResponse extends RequestResponseBase {

    @SerializedName(value = "city_id")
    public String cityId;
    @SerializedName(value = "city")
    public String cityName;
    public GetUserLocationInfoRequestResponse(ResponseListener responseListener) {
        super(responseListener);

        String url = domain +"location/getCity";
        Type jsonType = new TypeToken<ResponseInfo<GetUserLocationInfoRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetUserLocationInfoRequestResponse mResponse = (GetUserLocationInfoRequestResponse) responseInfo.response;
                    cityId = mResponse.cityId;
                    cityName = mResponse.cityName;
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
