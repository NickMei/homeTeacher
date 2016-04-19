package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Nick on 2016-04-12.
 */
public class TeachInfo {

    @SerializedName(value = "course_list")
    public List<CourseInfo> courseList;
    @SerializedName(value = "detail_location")
    public String detailLocation;
    @SerializedName(value = "way_list")
    public List<TeachingMethod> methodList;

    public class CourseInfo
    {
        @SerializedName(value = "teach_course_id")
        public String teachCourseId;
        public String stage;
        public String course;
        @SerializedName(value = "sub_course")
        public String subCourse;
        @SerializedName(value = "student_go_price")
        public String studentPrice;
        @SerializedName(value = "teacher_go_price")
        public String teacherPrice;
        @SerializedName(value = "discuss_place_price")
        public String discussedPlacePrice;
        @SerializedName(value = "course_str")
        public String courseStr;
    }

    public class TeachingMethod
    {
        @SerializedName(value = "way_id")
        public int methodId;
        @SerializedName(value = "way_key")
        public String methodKey;
        @SerializedName(value = "way_name")
        public String MethodName;

    }
}
