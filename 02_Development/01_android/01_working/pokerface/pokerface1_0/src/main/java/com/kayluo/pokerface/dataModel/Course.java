package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class Course {
    @SerializedName(value = "course_name")
    public String name;
    @SerializedName(value = "course_id")
    public String id;
    @SerializedName(value = "subcourse_list")
    public List<SubCourse> subCourseList;


}
