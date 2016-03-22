package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cxm170 on 2015/6/16.
 */
public class Province {
    @SerializedName(value = "province_id")
    public String provinceID;
    @SerializedName(value = "province_name")
    public String provinceName;
}
