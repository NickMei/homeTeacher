package com.kayluo.pokerface.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.Stage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class GetStageListRequestResponse extends RequestResponseBase {

    public List<Stage> stageList;
    public GetStageListRequestResponse(ResponseListener responseListener)
    {
        super(responseListener);
        stageList = new ArrayList<Stage>();
        String tag_json_obj = "json_obj_req";

        String url = domain +"course/getStageList";

        JSONArray responseArray = new JSONArray();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url, responseArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            Stage stage = new Stage();
                            stage.stageId = item.getString("stage_id");
                            stage.name = item.getString("stage");
                            stageList.add(stage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                GetStageListRequestResponse.this.listener.onCompleted(stageList);
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GetStageListRequestResponse.this.listener.onCompleted(error.toString());
            }
        });

        AppManager.shareInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
