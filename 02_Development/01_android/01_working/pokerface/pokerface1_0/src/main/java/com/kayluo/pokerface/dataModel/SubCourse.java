package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/16.
 */
public class SubCourse {
    @SerializedName(value = "course_sub_name")
    public String name;
    @SerializedName(value = "course_sub_id")
    public String id;

}
