package com.kayluo.pokerface.api.order;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.api.base.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.Order;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Nick on 2016-04-11.
 */
public class AddOrderRequestResponse extends RequestResponseBase {
    String result;
    Order order;
    public AddOrderRequestResponse(Order order, ResponseListener responseListener) {
        super(responseListener);
        this.order = order;
        this.url = domain +"order/addOrder";
        String jsonString = gson.toJson(order);
        requestJsonMap = (Map<String, Object>) gson.fromJson(jsonString, requestJsonMap.getClass());
        Type jsonType = new TypeToken<ResponseInfo<GetOrderTutorInfoRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    AddOrderRequestResponse mResponse = (AddOrderRequestResponse) responseInfo.response;
                    result = mResponse.result;
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
