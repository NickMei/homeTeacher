package com.kayluo.pokerface.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.Util.BitmapDownloaderTask;
import com.kayluo.pokerface.api.GetTutorDetailRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorDetail;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorEduInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorHonourExpInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorSuccessCaseInfo;

public class TutorDetailActivity extends AppCompatActivity {

    public String tutorId;
    private ImageView tutorPic;
    private Toolbar mToolbar;
    private TextView tutorName;
    private TextView career;
    private TextView university;
    private TextView signature;

    private TextView applauseRate;
    private TextView ratingCount;
    private TextView favCount;
    private RatingBar ratingBar;

    private TextView totalClassTime;
    private TextView numOfStudents;
    private TextView teachYears;

    private TextView courseInfo;
    private TextView locationInfo;
    private TextView price;

    private ImageView locationPic;
    private TextView selfIntro;

    private LinearLayout eduInfoLinearLayout;
    private LinearLayout successCaseInfoLinearLayout;
    private LinearLayout honourExpInfoLinearLayout;
    private LinearLayout commentInfoLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        tutorId = this.getIntent().getStringExtra("tutorId");
        mToolbar = (Toolbar) findViewById(R.id.tutor_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tutorPic = (ImageView) this.findViewById(R.id.tutor_pic);
        tutorName = (TextView) this.findViewById(R.id.tutor_detail_name);
        career = (TextView) this.findViewById(R.id.tutor_detail_career);
        university = (TextView) this.findViewById(R.id.tutor_detail_university);
        signature = (TextView) this.findViewById(R.id.tutor_detail_slogan);
        applauseRate = (TextView) this.findViewById(R.id.applauseRate);
        ratingCount = (TextView) this.findViewById(R.id.ratingCount);
        ratingBar = (RatingBar) this.findViewById(R.id.comment_rating_bar);
        totalClassTime = (TextView) this.findViewById(R.id.tutor_detail_total_class_time);
        numOfStudents = (TextView) this.findViewById(R.id.numOfStudents);
        teachYears = (TextView) this.findViewById(R.id.tutor_detail_teach_years);

        courseInfo = (TextView) this.findViewById(R.id.course_info);
        locationInfo = (TextView) this.findViewById(R.id.location_info);
        price = (TextView) this.findViewById(R.id.tutor_detail_price);

        locationPic = (ImageView) this.findViewById(R.id.tutor_detail_location);
        selfIntro = (TextView) this.findViewById(R.id.self_intro);

        successCaseInfoLinearLayout = (LinearLayout) this.findViewById(R.id.success_case_info);
        honourExpInfoLinearLayout = (LinearLayout) this.findViewById(R.id.honour_exp_info);
        commentInfoLinearLayout = (LinearLayout) this.findViewById(R.id.comment_info);
        eduInfoLinearLayout = (LinearLayout) this.findViewById(R.id.tutor_detail_edu_info);

        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED,returnIntent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void getData()
    {
        GetTutorDetailRequestResponse getTutorDetailRequestResponse = new GetTutorDetailRequestResponse(this.tutorId, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                TutorDetail detail = (TutorDetail) data;
                new BitmapDownloaderTask(tutorPic)
                        .execute(detail.basic_info.head_photo);
                tutorName.setText(detail.basic_info.name);
                career.setText(detail.basic_info.career);
                university.setText(detail.edu_info.get(0).university);
                signature.setText(detail.basic_info.signature);

//                favCount.setText(detail.like_info.num_of_like+ "");
                totalClassTime.setText(detail.record_info.total_class_time+ "小時");
                numOfStudents.setText(detail.record_info.total_students+ "位");
                teachYears.setText(detail.record_info.teach_years+ "年");
                //
                courseInfo.setText(detail.teach_info.course_info);
                locationInfo.setText(detail.teach_info.location_info);
                price.setText(detail.teach_info.price_info.price_min + " - " + detail.teach_info.price_info.price_max);

                new BitmapDownloaderTask(locationPic)
                        .execute(detail.basic_info.address_url);

                selfIntro.setText(detail.basic_info.self_intro);

                for (TutorEduInfo eduInfo: detail.edu_info)
                {
                    View view = getLayoutInflater().inflate(R.layout.element_edu_info,null);
                    TextView university = (TextView) view.findViewById(R.id.university);
                    TextView degree = (TextView) view.findViewById(R.id.degree);
                    TextView major = (TextView) view.findViewById(R.id.major);

                    degree.setText(eduInfo.edu_level + "(" + eduInfo.start_datetime.split("-")[0] + "-" + eduInfo.end_datetime.split("-")[0] + ")");
                    university.setText(eduInfo.university);
                    major.setText(eduInfo.major);

                    eduInfoLinearLayout.addView(view);
                }

                for(TutorSuccessCaseInfo caseInfo : detail.success_case_info)
                {
                    View view = getLayoutInflater().inflate(R.layout.element_success_case_info,null);
                    TextView title = (TextView) view.findViewById(R.id.case_title);
                    TextView subTitle = (TextView) view.findViewById(R.id.case_subtitle);
                    TextView content = (TextView) view.findViewById(R.id.case_content);

                    title.setText(caseInfo.stage + "/" + caseInfo.course + " " + caseInfo.start_datetime + " 至 " + caseInfo.end_datetime);
                    subTitle.setText(caseInfo.case_title);
                    content.setText(caseInfo.case_desc);

                    successCaseInfoLinearLayout.addView(view);
                }

                for(TutorHonourExpInfo expInfo : detail.honour_exp_info)
                {
                    View view = getLayoutInflater().inflate(R.layout.element_honour_exp_info,null);
                    TextView title = (TextView) view.findViewById(R.id.honour_exp_title);
                    TextView content = (TextView) view.findViewById(R.id.honour_exp_content);

                    title.setText(expInfo.case_title + " " + expInfo.start_datetime + " 至 " + expInfo.end_datetime);
                    content.setText(expInfo.case_desc);

                    honourExpInfoLinearLayout.addView(view);
                }

                //comment_info
                applauseRate.setText(detail.comment_info.rating);
                ratingCount.setText(detail.comment_info.num_of_comments + "");
                ratingBar.setRating(Float.parseFloat(detail.comment_info.rating));


                View view = getLayoutInflater().inflate(R.layout.element_comment_info,null);
                ImageView commentUserPic = (ImageView) view.findViewById(R.id.comment_user_pic);
                TextView commentUsername = (TextView) view.findViewById(R.id.comment_user_name);
                TextView commentTitle = (TextView) view.findViewById(R.id.comment_title);
                TextView commentDatetime = (TextView) view.findViewById(R.id.comment_datetime);
                TextView commentRating = (TextView) view.findViewById(R.id.comment_rating);
                TextView commentContent = (TextView) view.findViewById(R.id.comment_content);

                new BitmapDownloaderTask(commentUserPic)
                        .execute(detail.comment_info.comment.student_photo_url);
                commentUsername.setText(detail.comment_info.comment.student_name);
                commentTitle.setText(detail.comment_info.comment.comment_type);
                commentDatetime.setText(detail.comment_info.comment.create_datetime);
                commentRating.setText(detail.comment_info.comment.comment_type + "評");
                commentContent.setText(detail.comment_info.comment.comment);

                commentInfoLinearLayout.addView(view,2);


            }

        });

    }



}
