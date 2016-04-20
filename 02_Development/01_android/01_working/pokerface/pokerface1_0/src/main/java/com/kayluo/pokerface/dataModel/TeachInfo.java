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
        public String courseId;
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
        public final static String TEACHING_METHOD_DISCUSSABLE = "discuss_place";
        public final static String TEACHING_METHOD_TEACHER = "teacher_go";
        public final static String TEACHING_METHOD_STUDENT = "student_go";
        @SerializedName(value = "way_id")
        public String methodId;
        @SerializedName(value = "way_key")
        public String methodKey;
        @SerializedName(value = "way_name")
        public String methodName;

    }
}
