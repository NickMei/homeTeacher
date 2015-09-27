package com.kayluo.pokerface.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.Stage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/13.
 */
public class GetCourseListRequestResponse extends RequestResponseBase  {
    public List<Course> courseList;
    public GetCourseListRequestResponse(String stageId,ResponseListener responseListener)
    {
        super(responseListener);
        courseList = new ArrayList<Course>();
        String tag_json_obj = "json_obj_req";

        String url = domain + "course/getCourseList?stage_id=" + stageId;

        JSONArray responseArray = new JSONArray();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url, responseArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            Course course = new Course();
                            course.name = item.getString("course_name");
                            course.id = item.getString("course_id");
                            courseList.add(course);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listener.onCompleted(courseList);
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onCompleted(error.toString());
            }
        });

        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

}
