package com.kayluo.pokerface.api.studentCenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TransactionRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/9/15.
 */
public class GetStudentOrderListRequestResponse extends RequestResponseBase {

    public ArrayList<TransactionRecord> transactionRecordList;
    public GetStudentOrderListRequestResponse(String orderType,ResponseListener responselistener) {
        super(responselistener);

        this.requestJsonMap.put("order_type", orderType);
        this.url = domain +"studentcenter/getOrderList";
        Type jsonType = new TypeToken<ResponseInfo<MyResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(this.url, jsonType, this.requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    MyResponse mResponse = (MyResponse) responseInfo.response;
                    transactionRecordList = mResponse.list;
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

    private class MyResponse
    {
        public String count;
        public ArrayList<TransactionRecord> list;
    }

}
