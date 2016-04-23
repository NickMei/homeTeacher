package com.kayluo.pokerface.api.tutorInfo;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.api.base.GsonRequest;
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
                if (responseInfo.returnCode == EReturnCode.SUCCESS.getValue()) {
                    GetTutorListRequestResponse mResponse = (GetTutorListRequestResponse) responseInfo.response;
                    tutorList = mResponse.tutorList;
                }
                listener.onCompleted(responseInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tutorList = new ArrayList<TutorEntity>();
                onVolleyError(error);
            }
        });

        AppManager.shareInstance().addToRequestQueue(gsonRequest, this.url);
    }

    public class Params
    {
        public Params() {
            UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
            AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();

            city_id = userConfig.isSignedIn ? userConfig.profile.city.cityID : appConfig.locationCity.cityID;
            is_app_query = 1;
            //course type
            stage = "";
            course = "";
            sub_course = "";
            tutor_name = "";
            //pagination
            page = "1";
            limit = "6";
            // by filter
            gender_eng = "";
            career = "";
            district = "";
            price_min = "";
            price_max = "";
            day_eng = "all";
            period_eng = "all";
            city_id = "";
            // by order
            order_by = "general";
            order_direc = "";
            longitude = "";
            latitude = "";

        }
        public int is_app_query = 1;
        //course type
        public String stage;
        public String course;
        public String sub_course;
        public String tutor_name;
        //paging
        public String page;
        public String limit;
        // by filter
        public String gender_eng ;
        public String career;
        public String district;
        public String price_min;
        public String price_max;
        public String day_eng;
        public String period_eng;
        public String city_id;
        // by order
        public String order_by;


        public String order_direc;
        public String longitude;
        public String latitude;

    }

}
