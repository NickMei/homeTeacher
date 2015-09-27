package com.kayluo.pokerface.api;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.Province;
import com.kayluo.pokerface.dataModel.TutorEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/6/16.
 */
public class GetProvinceListRequestResponse extends RequestResponseBase {

    public List<Province> provinceList;
    public GetProvinceListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);
        provinceList = new ArrayList<Province>();
        String tag_json_obj = "json_obj_req";

        String url = domain +"location/getProvinceList";

        StringRequest jsonObjReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Province>>(){}.getType();
                provinceList = gson.fromJson(response.toString(),listType);
                Log.e("tutorList","size :"+provinceList.size());
                listener.onCompleted(provinceList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}
