package com.kayluo.pokerface.ui.tutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.ui.order.ConfirmBookingActivity;
import com.kayluo.pokerface.ui.user.commentRecord.CommentRecordActivity;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.util.Utils;
import com.kayluo.pokerface.api.studentCenter.GetCommentListRequestResponse;
import com.kayluo.pokerface.api.tutorInfo.GetTutorDetailRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorDetail;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorEduInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorHonourExpInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorSuccessCaseInfo;
import com.kayluo.pokerface.dataModel.TutorDetail.TutorTimeTableInfo;

import java.util.ArrayList;

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

    private ProgressDialog progressDialog;

    private TextView verfID;
    private TextView verfDegree;
    private TextView verfTeacher;
    private TextView verfExpertise;

    private Button moreCommentsButton;


    private TextView bookmarkButton;
    private TextView confirmBookingButton;

    private GetTutorDetailRequestResponse getTutorDetailRequestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        tutorId = this.getIntent().getStringExtra("tutorId");
        initToolBar();
        setUpViews();
        getData();
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.tutor_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }

    private void setUpViews()
    {
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

        verfID = (TextView) this.findViewById(R.id.verf_id);
        verfDegree = (TextView) this.findViewById(R.id.verf_degree);
        verfExpertise = (TextView) this.findViewById(R.id.verf_degree);
        verfTeacher = (TextView) this.findViewById(R.id.verf_teacher);

        moreCommentsButton = (Button) this.findViewById(R.id.more_comments);

        bookmarkButton = (TextView) this.findViewById(R.id.tutor_detail_bookmark_tutor_button);
        confirmBookingButton = (TextView) this.findViewById(R.id.tutor_detail_confirm_booking_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("获取教师详情...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void getData()
    {
        getTutorDetailRequestResponse = new GetTutorDetailRequestResponse(this.tutorId, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo data) {
                final TutorDetail detail = getTutorDetailRequestResponse.tutorDetail;
                new BitmapDownloaderTask(tutorPic)
                        .execute(detail.basic_info.head_photo);
                tutorName.setText(detail.basic_info.name);
                career.setText(detail.basic_info.career);
                university.setText(detail.edu_info.get(0).university);
                signature.setText(detail.basic_info.signature);

                totalClassTime.setText(detail.record_info.total_class_time+ "小時");
                numOfStudents.setText(detail.record_info.total_students+ "位");
                teachYears.setText(detail.record_info.teach_years+ "年");

                courseInfo.setText(detail.teach_info.course_info);
                locationInfo.setText(detail.teach_info.location_info);
                price.setText(detail.teach_info.price_info.price_min + " - " + detail.teach_info.price_info.price_max);

                verfExpertise.setEnabled(detail.auth_info.is_professionalCer_auth);
                verfID.setEnabled(detail.auth_info.is_identification_auth);
                verfTeacher.setEnabled(detail.auth_info.is_teacherCer_auth);
                verfDegree.setEnabled(detail.auth_info.is_eduCer_auth);

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
                moreCommentsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TutorDetailActivity.this, CommentRecordActivity.class);
                        intent.putExtra("targetType", GetCommentListRequestResponse.TargetType.TEACHER.name());
                        intent.putExtra("tutorID", tutorId);
                        startActivity(intent);
                    }
                });

                createTableViewCell(detail.timetable_info);

                bookmarkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                confirmBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TutorDetailActivity.this, ConfirmBookingActivity.class);
                        intent.putExtra("tutorID", tutorId);
                        startActivity(intent);
                    }
                });

                progressDialog.dismiss();
            }

        });

    }

    private void createTableViewCell(ArrayList<TutorTimeTableInfo> timetable_info)
    {

        for(TutorTimeTableInfo tableInfo : timetable_info)
        {
            TableRow tableRow = null;


            if (tableInfo.time.equals("上午"))
            {
                tableRow = (TableRow) findViewById(R.id.table_row_morning);
            }else if (tableInfo.time.equals("下午")){
                tableRow = (TableRow) findViewById(R.id.table_row_afternoon);
            }else{
                tableRow = (TableRow) findViewById(R.id.table_row_evening);
            }
            tableRow.addView(createTextView(tableInfo.time));
            tableRow.addView(createImageView(tableInfo.monday.equals("1")));
            tableRow.addView(createImageView(tableInfo.tuesday.equals("1")));
            tableRow.addView(createImageView(tableInfo.wednesday.equals("1")));
            tableRow.addView(createImageView(tableInfo.thursday.equals("1")));
            tableRow.addView(createImageView(tableInfo.friday.equals("1")));
            tableRow.addView(createImageView(tableInfo.saturday.equals("1")));
            tableRow.addView(createImageView(tableInfo.sunday.equals("1")));
        }


    }

    private TextView createTextView(String text)
    {

        TextView timeTextView = new TextView(this);
        timeTextView.setText(text);
        timeTextView.setBackgroundColor(getResources().getColor(R.color.primaryBackgroundColor));
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(1, 1, 1, 1);
        timeTextView.setLines(1);
        timeTextView.setLayoutParams(layoutParams);
        return timeTextView;

    }

    private ImageView createImageView(Boolean hasClass)
    {

        ImageView timeImageView = new ImageView(this);
        if (hasClass){
            timeImageView.setImageResource(R.drawable.ic_check_black_24dp);
        }
        timeImageView.setBackgroundColor(getResources().getColor(R.color.primaryBackgroundColor));
//        timeImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(Utils.getPixels(25,this), TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(1, 1, 1, 1);
        timeImageView.setLayoutParams(layoutParams);
        return timeImageView;

    }







}
