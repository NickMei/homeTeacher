package com.kayluo.pokerface.api.tutorInfo;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GsonRequest;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by cxm170 on 2015/8/2.
 */
public class GetTutorListRequestResponse extends RequestResponseBase {

    @SerializedName(value = "list")
    public ArrayList<TutorEntity> tutorList;
    public String count;

    public GetTutorListRequestResponse()
    {
        super(null);
    }
    public GetTutorListRequestResponse(Params params,ResponseListener responseListener)
    {
        super(responseListener);
        this.url = domain +"tutorinfo/getTutorList";
        String jsonString = gson.toJson(params);
        requestJsonMap = (Map<String, Object>) gson.fromJson(jsonString, requestJsonMap.getClass());
        Type jsonType = new TypeToken<ResponseInfo<GetTutorListRequestResponse>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(url, jsonType, requestJsonMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ResponseInfo responseInfo = (ResponseInfo) response;
                if (responseInfo.returnCode == 0) {
                    GetTutorListRequestResponse mResponse = (GetTutorListRequestResponse) responseInfo.response;
                    tutorList = mResponse.tutorList;
                }
                listener.onCompleted(responseInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ResponseInfo responseInfo = new ResponseInfo();
                responseInfo.returnCode = -999;
                tutorList = new ArrayList<TutorEntity>();
                listener.onCompleted(responseInfo);
            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);
    }

    public class Params
    {
        public Params() {
            UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
            AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
            if (userConfig.isSignedIn) {
                city_id = userConfig.profile.city.cityID;
            } else
            {
                city_id = appConfig.locationCity.cityID;
            }
    }
        public int is_app_query = 1;
        //course type
        public String stage = "";
        public String course = "";
        public String sub_course = "";
        public String tutor_name = "";
        //paging
        public String page = "1";
        public String limit = "6";
        // by filter
        public String gender_eng = "";
        public String career = "";
        public String district = "";
        public String price_min = "";
        public String price_max = "";
        public String day_eng = "all";
        public String period_eng = "all";
        public String city_id = "";
        // by order
        public String order_by = "general";
        public String order_direc = "";
        public String longitude = "";
        public String latitude = "";

    }

}
