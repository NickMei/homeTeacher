package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cxm170 on 2015/8/26.
 */
public class City {
    @SerializedName(value = "CityID")
    public String cityID;
    @SerializedName(value = "CityName")
    public String cityName;
}
