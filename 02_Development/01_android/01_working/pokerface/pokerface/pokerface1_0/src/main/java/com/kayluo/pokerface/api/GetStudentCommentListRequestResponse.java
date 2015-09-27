package com.kayluo.pokerface.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.StudentComment;
import com.kayluo.pokerface.dataModel.TransactionRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/9/21.
 */
public class GetStudentCommentListRequestResponse extends RequestResponseBase {
    public ArrayList<StudentComment> list;
    public GetStudentCommentListRequestResponse(String orderType,ResponseListener responseListener) {
        super(responseListener);

        Map<String,String> hashedMap = new LinkedHashMap<String,String>();
        hashedMap.put("order_type", orderType);
        this.jsonString = gson.toJson(hashedMap);
        this.url = domain +"studentcenter/getStudentOrderList";
        Type jsonType = new TypeToken<ResponseInfo<MyResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, null, jsonString, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode.equals("0")) {
                    MyResponse mResponse = (MyResponse) responseInfo.response;
                    list = mResponse.list;
                }
                listener.onCompleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, tag_json_obj);
    }
    private class MyResponse
    {
        public String count;
        public ArrayList<StudentComment> list;
    }
}
