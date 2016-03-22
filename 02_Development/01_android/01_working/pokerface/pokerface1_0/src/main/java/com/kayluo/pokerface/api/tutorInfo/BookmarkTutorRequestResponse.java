package com.kayluo.pokerface.api.tutorInfo;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import java.lang.reflect.Type;

/**
 * Created by Nick on 2016-03-22.
 */
public class BookmarkTutorRequestResponse extends RequestResponseBase
{
    public int result;
    public BookmarkTutorRequestResponse(String tutorId, Boolean isBookmarked,ResponseListener responseListener) {
        super(responseListener);
        this.url = domain +"tutorinfo/bookmarkTutor";
        this.requestJsonMap.put("tutor_id",tutorId);
        this.requestJsonMap.put("is_bookmarked", isBookmarked ? "1": "0");
        Type jsonType = new TypeToken<ResponseInfo<BookmarkTutorRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, this.requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    BookmarkTutorRequestResponse myResponse =  (BookmarkTutorRequestResponse) responseInfo.response;
                    result = myResponse.result;
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
