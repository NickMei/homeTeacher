package com.kayluo.pokerface.api;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.District;
import com.kayluo.pokerface.dataModel.Province;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/8/26.
 */
public class GetDistrictListRequestResponse extends RequestResponseBase {
    public List<District> districtList;
    public GetDistrictListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);
        districtList = new ArrayList<District>();
        String tag_json_obj = "json_obj_req";

        String url = domain +"location/getProvinceList";

        StringRequest jsonObjReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<District>>(){}.getType();
                districtList = gson.fromJson(response.toString(),listType);
                Log.e("tutorList", "size :" + districtList.size());
                listener.onCompleted(districtList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
