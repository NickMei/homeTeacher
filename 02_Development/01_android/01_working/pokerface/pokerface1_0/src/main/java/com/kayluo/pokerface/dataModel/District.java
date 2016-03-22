package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cxm170 on 2015/8/26.
 */
public class District {
    @SerializedName(value = "district_id")
    public String id;
    @SerializedName(value = "district_name")
    public String name;
}
