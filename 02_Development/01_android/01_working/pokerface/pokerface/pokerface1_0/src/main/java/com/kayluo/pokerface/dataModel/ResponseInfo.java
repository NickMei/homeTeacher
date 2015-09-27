package com.kayluo.pokerface.dataModel;


import com.google.gson.annotations.SerializedName;

/**
 * Created by cxm170 on 2015/9/3.
 */
public class ResponseInfo<T> {
    @SerializedName(value = "retcode")
    public String returnCode;
    @SerializedName(value = "retmsg")
    public String message;
    public T response;
}
