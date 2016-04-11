package com.kayluo.pokerface.core;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kayluo.pokerface.util.MD5Hash;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxm170 on 2015/9/3.
 */
public class GsonRequest<T> extends Request<T> {
    private Gson gson  = new Gson();
    private Type jsonType;
    private Map<String, String> headers;
    private Response.Listener<T> listener;
    private Map<String,String> jsonMap;
    /**
     * Make a GET request and return a parsed object from JSON.
     * @param url URL of the request to make
     * @param jsonType Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     * @param jsonMap body Json Map
     * @param listener
     */
    public GsonRequest(String url, Type jsonType, Map<String, String> headers, Map<String,String> jsonMap,
                       Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.jsonType = jsonType;
        this.headers = headers;
        this.listener = listener;
        if (jsonMap.size() != 0){
            this.jsonMap = jsonMap;
        }else{
            this.jsonMap = new HashMap<>();
        }
    }

    public GsonRequest(String url, Type jsonType, Map<String,String> jsonMap,
                       Response.Listener listener, Response.ErrorListener errorListener) {
        this(url, jsonType, null, jsonMap, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (headers == null)
        {
            UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();

            Map<String,Object> hashedMap = new LinkedHashMap<String,Object>();
            hashedMap.put("request_str","111111");
            hashedMap.put("token",userConfig.profile.token);
            hashedMap.put("request_data", gson.toJson(jsonMap));
            String checkSumString = gson.toJson(hashedMap);

            Map<String, String>  headerParams = new HashMap<String, String>();
            headerParams.put("uid", userConfig.userId );
            headerParams.put("identity", "student");
            headerParams.put("checksum", MD5Hash.md5(checkSumString));

            return headerParams;

        }
        else if(headers.size() != 0) {
            return headers;
        }else{
            return super.getHeaders();
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String jsonString = gson.toJson(jsonMap);
        Log.i("body jsonString",jsonString);
        return jsonString.getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String encoder = HttpHeaderParser.parseCharset(response.headers);
            String json = new String(response.data,encoder);

            Log.e("response",json.replaceAll("\\r\\n|\\r|\\n", " "));

            ResponseInfo responseInfo = gson.fromJson(json,jsonType);

            return (Response<T>) Response.success(
                    responseInfo,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingExp message",e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.e("JsonException message",e.getMessage());
            Log.e("JsonException message","API name" + getUrl());
            return Response.error(new ParseError(e));
        }
    }

}