package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nick on 2016-04-12.
 */
public class Order {

    @SerializedName(value = "tutor_id")
    public String tutorId;
    @SerializedName(value = "student_id")
    public String studentId;
    @SerializedName(value = "course_id")
    public String courseId;
    @SerializedName(value = "way_id")
    public String wayId;
    @SerializedName(value = "price")
    public String price;
    @SerializedName(value = "class_address")
    public String classAddress;
    @SerializedName(value = "student_name")
    public String studentName;
    @SerializedName(value = "extra_info")
    public String extraInfo;
    @SerializedName(value = "class_time")
    public String classTime;
}
