package com.kayluo.pokerface.core;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Authenticator;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.kayluo.pokerface.UI.user.LoginViewActivity;
import com.kayluo.pokerface.Util.MD5Hash;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cxm170 on 2015/9/3.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Type jsonType;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final String jsonString;
    /**
     * Make a GET request and return a parsed object from JSON.
     * @param url URL of the request to make
     * @param jsonType Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     * @param jsonString body Json string
     * @param listener
     */
    public GsonRequest(String url, Type jsonType, Map<String, String> headers, String jsonString,
                       Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.jsonType = jsonType;
        this.headers = headers;
        this.listener = listener;
        this.jsonString = jsonString;
        Log.i("json String:",jsonString);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (headers == null)
        {
            UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();

            Map<String,String> hashedMap = new LinkedHashMap<String,String>();
            hashedMap.put("request_str","111111");
            hashedMap.put("token",userConfig.token);
            hashedMap.put("request_data",jsonString);
            String checkSumString = gson.toJson(hashedMap);

            Map<String, String>  params = new HashMap<String, String>();
            params.put("uid", userConfig.userId );
            params.put("identity", "student");
            params.put("checksum", MD5Hash.md5(checkSumString));

            Log.e("response checksum:", MD5Hash.md5(checkSumString));

            return params;

        }
        else{
            return headers;
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return jsonString.getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String encoder = HttpHeaderParser.parseCharset(response.headers);
            String json = new String(response.data, encoder == null? "UTF-8":encoder);

            Log.e("response string:",json);

            ResponseInfo responseInfo = gson.fromJson(json,jsonType);

            return (Response<T>) Response.success(
                        responseInfo,
                        HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingExp message",e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.e("JsonException message",e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

}