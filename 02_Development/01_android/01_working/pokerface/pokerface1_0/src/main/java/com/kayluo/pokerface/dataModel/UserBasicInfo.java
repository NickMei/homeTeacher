package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cxm170 on 2015/9/9.
 */
public class UserBasicInfo {
    public String name;
    public String nickname = "";
    public String gender;
    public String grade;
    public String address;
    @SerializedName(value = "city_id")
    public String cityId = "";
    public String head_photo;
    public String self_intro = "";
}
