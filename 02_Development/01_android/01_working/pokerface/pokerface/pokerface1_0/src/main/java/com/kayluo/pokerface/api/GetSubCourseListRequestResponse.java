package com.kayluo.pokerface.api;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.SubCourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class GetSubCourseListRequestResponse extends RequestResponseBase {

    public List<SubCourse> subCourseList;
    public GetSubCourseListRequestResponse(String courseId, ResponseListener responseListener)
    {
        super(responseListener);
        subCourseList = new ArrayList<SubCourse>();
        String tag_json_obj = "json_obj_req";

        String url = domain +"course" +
                "/getSubCourseList?course_id=" + courseId ;

        JSONArray responseArray = new JSONArray();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url, responseArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            SubCourse subCourse = new SubCourse();
                            subCourse.id = item.getString("course_sub_id");
                            subCourse.name = item.getString("course_sub_name");
                            subCourseList.add(subCourse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listener.onCompleted(subCourseList);
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onCompleted(error.toString());
            }
        });

        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
