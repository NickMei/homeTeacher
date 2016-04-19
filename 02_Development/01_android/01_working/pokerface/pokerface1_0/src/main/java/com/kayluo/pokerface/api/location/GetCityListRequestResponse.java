package com.kayluo.pokerface.api.location;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/8/26.
 */
public class GetCityListRequestResponse  extends RequestResponseBase {

    private String provinceID;
    public List<City> cityList;
    public GetCityListRequestResponse(String provinceID,ResponseListener responseListener)
    {
        super(responseListener);
        this.provinceID = provinceID;
        cityList = new ArrayList<City>();
        String tag_json_obj = "json_obj_req";

        String url = domain +"locationcontroller/getCityList?provinceID=" + this.provinceID;

        StringRequest jsonObjReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<City>>(){}.getType();
                cityList = gson.fromJson(response.toString(),listType);
                Log.e("cityList", "size :" + cityList.size());

                listener.onCompleted(new ResponseInfo());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppManager.shareInstance().addToRequestQueue(jsonObjReq, this.url);
    }
}
