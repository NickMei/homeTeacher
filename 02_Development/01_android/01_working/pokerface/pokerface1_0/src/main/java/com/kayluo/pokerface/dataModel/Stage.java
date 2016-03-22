package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/13.
 */
public class Stage {
    @SerializedName(value = "stage_id")
    public String stageId;
    @SerializedName(value = "stage")
    public String name;
    @SerializedName(value = "course")
    public List<Course> courseList;

}
