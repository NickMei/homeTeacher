package com.kayluo.pokerface.api;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.Util.URLUtils;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.TutorEntity;

import org.apache.http.client.utils.URLEncodedUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by cxm170 on 2015/8/2.
 */
public class GetTutorListRequestResponse extends RequestResponseBase {

    public String stage = "",
            course = "",
            subCourse = "",
            tutor_name = "",
            page = "1",
            limit = "6",
            gender_eng = "",
            career = "",
            district = "",
            price_min = "",
            price_max = "",
            day_eng = "",
            period_eng = "",
            order_by = "general",
            order_direc = "";
    public ArrayList<TutorEntity> tutorList;
    public GetTutorListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);
        tutorList = new ArrayList<TutorEntity>();
    }

    public void sendRequest()
    {
        String tag_json_obj = "json_obj_req";

        String url = domain  +"tutorinfo" + "/getTutorList";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields)
        {

            try {
                Object object = field.get(this);
                if (object instanceof String) {
                    url = URLUtils.addParameter(url, field.getName(), (String) object);
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        Log.i("string: ", url);
        StringRequest jsonObjReq = new StringRequest(url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
//                Type listType = new TypeToken<List<TutorEntity>>(){}.getType();
//                tutorList = gson.fromJson(response.toString(),listType);
//                Log.e("tutorList","size :"+tutorList.size());
//                listener.success(tutorList);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onCompleted(error.toString());
            }
        });

        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }



}
