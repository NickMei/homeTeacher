package com.kayluo.pokerface.api.studentCenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.CommentRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/9/21.
 */
public class GetCommentListRequestResponse extends RequestResponseBase {
    public ArrayList<CommentRecord> list;
    public enum TargetType {
        TEACHER {
            public String toString() {
                return "tutor";
            }
        },

        STUDENT {
            public String toString() {
                return "student";
            }
        }
    }
    public GetCommentListRequestResponse(TargetType targetType,String commentType, String tutorID , ResponseListener responseListener) {
        super(responseListener);
        requestJsonMap.put("target", targetType.toString());
        requestJsonMap.put("comment_type", commentType);
        requestJsonMap.put("tutor_id",tutorID);

        this.url = domain +"studentcenter/getCommentList";
        Type jsonType = new TypeToken<ResponseInfo<MyResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    MyResponse mResponse = (MyResponse) responseInfo.response;
                    list = mResponse.list;
                }
                listener.onCompleted(new ResponseInfo());
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
        public ArrayList<CommentRecord> list;
    }
}
