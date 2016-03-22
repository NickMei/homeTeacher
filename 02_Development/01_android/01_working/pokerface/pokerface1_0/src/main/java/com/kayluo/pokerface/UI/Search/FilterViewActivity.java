package com.kayluo.pokerface.ui.search;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.OnDialogButtonClickListener;
import com.kayluo.pokerface.api.location.GetDistrictByCityNameRequestResponse;
import com.kayluo.pokerface.api.tutorInfo.GetPriceRangeListRequestResponse;
import com.kayluo.pokerface.api.tutorInfo.GetTutorListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.ActivityRequestCode;
import com.kayluo.pokerface.component.SelectDayDialog;
import com.kayluo.pokerface.component.SelectDistrictDialog;
import com.kayluo.pokerface.component.SelectPriceRangeDialog;
import com.kayluo.pokerface.component.SelectTimeDialog;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.District;
import com.kayluo.pokerface.dataModel.PriceRange;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.Stage;
import com.kayluo.pokerface.dataModel.SubCourse;

import java.util.ArrayList;
import java.util.List;

public class FilterViewActivity extends AppCompatActivity implements OnDialogButtonClickListener {

    GetTutorListRequestResponse.Params params;
    private RequestResponseBase.ResponseListener responseListener;

    private Toolbar mToolbar;
    private Button selectCourseButton;
    private Button dayInfoSelectorBtn;
    private Button timeInfoSelectorBtn;
    private Button districtInfoSelectorBtn;
    private Button orderByDefaultBtn;
    private Button orderByRatingBtn;
    private Button orderByTotalClassTimeBtn;
    private Button orderByPriceBtn;
    private Button genderAllBtn;
    private Button genderMaleBtn;
    private Button genderFemaleBtn;
    private Button identityAllBtn;
    private Button identityCollegeStudentBtn;
    private Button identityServiceTeacherBtn;
    private Button identityParttimeTeacherBtn;
    private Button filterSubmitBtn;
    private Button priceRangeSelectorBtn;
    private SelectTimeDialog selectTimeDialog;
    private SelectDayDialog selectDayDialog;
    private SelectDistrictDialog selectDistrictDialog;
    private SelectPriceRangeDialog selectPriceRangeDialog;

    GetTutorListRequestResponse getTutorListRequestResponse;
    GetDistrictByCityNameRequestResponse getDistrictByCityNameRequestResponse;
    GetPriceRangeListRequestResponse getPriceRangeListRequestResponse;

    int stageIndex;
    int courseIndex;
    int subCourseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);
        initToolBar();
        setUpViews();
        Intent intent = getIntent();
        // load previous state
        String serializedString = intent.getStringExtra("params");
        params = new Gson().fromJson(serializedString, GetTutorListRequestResponse.Params.class);
        stageIndex = getIntent().getIntExtra("stageIndex", 0);
        courseIndex = getIntent().getIntExtra("courseIndex", 0);
        subCourseIndex = getIntent().getIntExtra("subCourseIndex", 0);
        sendAPIRequest();
        loadSavedState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ActivityRequestCode.SELECT_COURSE)
        {
            if (resultCode == RESULT_OK)
            {
                stageIndex = data.getIntExtra("stageIndex", 0);
                courseIndex = data.getIntExtra("courseIndex", 0);
                subCourseIndex = data.getIntExtra("subCourseIndex", 0);
                List<Stage> stageList = AppManager.shareInstance().settingManager.getAppConfig().stageList;
                params.stage = "";
                params.course = "";
                params.subCourse = "";
                if (stageIndex != 0)
                {
                    Stage stage = stageList.get(stageIndex);
                    params.stage = stage.name;
                    if (courseIndex != 0)
                    {
                        Course course = stage.courseList.get(courseIndex);
                        params.course = course.name;
                        if (subCourseIndex != 0 && course.subCourseList.size() > 0)
                        {
                            SubCourse subCourse = course.subCourseList.get(subCourseIndex);
                            params.subCourse = subCourse.name;
                        }
                    }
                }

                selectCourseButton.setText(getSelectedCourse());
                getTutorListData();
            }
        }
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.filter_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               navigateBack();
            }
        });

    }

    public void navigateBack()
    {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        String serializedString = new Gson().toJson(params);
        returnIntent.putExtra("params", serializedString);
        returnIntent.putExtra("stageIndex",stageIndex);
        returnIntent.putExtra("courseIndex", courseIndex);
        returnIntent.putExtra("subCourseIndex",subCourseIndex);
        finish();

    }

    private void sendAPIRequest()
    {
        responseListener = new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo responseInfo) {
                if (responseInfo.returnCode == 0) {
                    if (getTutorListRequestResponse.tutorList.size() == 0) {
                        filterSubmitBtn.setText("无结果！");
                    } else {
                        filterSubmitBtn.setText("显示" + getTutorListRequestResponse.tutorList.size() + "项搜索结果");
                    }
                    filterSubmitBtn.setVisibility(View.VISIBLE);
                }
            }
        };
        getTutorListRequestResponse = new GetTutorListRequestResponse(params,responseListener);
    }
    private void setUpViews(){

        selectCourseButton = (Button) findViewById(R.id.filter_view_select_course_button);
        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterViewActivity.this,SelectCourseActivity.class);
                intent.putExtra("stageIndex",stageIndex);
                intent.putExtra("courseIndex",courseIndex);
                intent.putExtra("subCourseIndex",subCourseIndex);
                startActivityForResult(intent, ActivityRequestCode.SELECT_COURSE);
            }
        });
        selectDayDialog = new SelectDayDialog();
        selectTimeDialog= new SelectTimeDialog();
        dayInfoSelectorBtn = (Button) findViewById(R.id.filter_view_day_selector_button);
        dayInfoSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDayDialog.show(getFragmentManager(),"");
            }
        });
        timeInfoSelectorBtn = (Button) findViewById(R.id.filter_view_time_selector_button);
        timeInfoSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTimeDialog.show(getFragmentManager(), "");
            }
        });
        districtInfoSelectorBtn = (Button) findViewById(R.id.filter_view_district_selector_button);


        districtInfoSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectDistrictDialog != null)
                {
                    selectDistrictDialog.show(getFragmentManager(),"");
                }

            }
        });

        priceRangeSelectorBtn = (Button) findViewById(R.id.filter_view_price_filter_button);
        priceRangeSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPriceRangeDialog.show(getFragmentManager(), "");
            }
        });

        orderByDefaultBtn = (Button) findViewById(R.id.filter_view_order_by_default);

        orderByRatingBtn = (Button) findViewById(R.id.filter_view_order_by_rating);
        orderByTotalClassTimeBtn = (Button) findViewById(R.id.filter_view_order_by_total_class_time);
        orderByPriceBtn = (Button) findViewById(R.id.filter_view_order_by_price_asc);

        genderAllBtn = (Button) findViewById(R.id.filter_view_gender_all);
        genderFemaleBtn = (Button) findViewById(R.id.filter_view_gender_female);
        genderMaleBtn = (Button) findViewById(R.id.filter_view_gender_male);

        identityAllBtn = (Button) findViewById(R.id.filter_view_identity_all);
        identityServiceTeacherBtn = (Button) findViewById(R.id.filter_view_identity_service_teacher);
        identityCollegeStudentBtn = (Button) findViewById(R.id.filter_view_identity_collage_student);
        identityParttimeTeacherBtn = (Button) findViewById(R.id.filter_view_identity_parttime_teacher);

        loadUserDistrictInfo();
        loadPriceRangeList();

        Button.OnClickListener orderClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                orderByDefaultBtn.setSelected(false);
                orderByRatingBtn.setSelected(false);
                orderByTotalClassTimeBtn.setSelected(false);
                orderByPriceBtn.setSelected(false);
                ((Button)view).setSelected(true);
                if (view == orderByDefaultBtn)
                {
                    params.order_by = "general";
                }
                else if (view == orderByRatingBtn)
                {
                    params.order_by = "rating";
                }
                else if (view == orderByTotalClassTimeBtn)
                {
                    params.order_by = "total_class_time";
                }
                else if (view == orderByPriceBtn)
                {
                    params.order_by = "price";
                }

                getTutorListData();
            }
        };

        orderByDefaultBtn.setOnClickListener(orderClickListener);
        orderByRatingBtn.setOnClickListener(orderClickListener);
        orderByTotalClassTimeBtn.setOnClickListener(orderClickListener);
        orderByPriceBtn.setOnClickListener(orderClickListener);


        Button.OnClickListener identityClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                identityAllBtn.setSelected(false);
                identityServiceTeacherBtn.setSelected(false);
                identityCollegeStudentBtn.setSelected(false);
                identityParttimeTeacherBtn.setSelected(false);
                ((Button)view).setSelected(true);
                if (view == identityAllBtn)
                {
                    params.career = "";
                }
                else
                {
                    params.career = ((Button) view).getText().toString();
                }
                getTutorListData();
            }
        };
        identityAllBtn.setOnClickListener(identityClickListener);
        identityServiceTeacherBtn.setOnClickListener(identityClickListener);
        identityCollegeStudentBtn.setOnClickListener(identityClickListener);
        identityParttimeTeacherBtn.setOnClickListener(identityClickListener);

        Button.OnClickListener genderClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                genderAllBtn.setSelected(false);
                genderFemaleBtn.setSelected(false);
                genderMaleBtn.setSelected(false);

                ((Button)view).setSelected(true);
                if (view == genderAllBtn)
                {
                    params.gender_eng = "";
                }
                else if (view == genderFemaleBtn)
                {
                    params.gender_eng = "female";
                }else
                {
                    params.gender_eng ="male";
                }
                getTutorListData();
            }
        };

        genderAllBtn.setOnClickListener(genderClickListener);
        genderFemaleBtn.setOnClickListener(genderClickListener);
        genderMaleBtn.setOnClickListener(genderClickListener);

        filterSubmitBtn = (Button) findViewById(R.id.filter_submit_button);
        filterSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });

    }

    private void loadPriceRangeList(){
        getPriceRangeListRequestResponse = new GetPriceRangeListRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == 0)
                {
                    ArrayList<String> list = new ArrayList<String>();
                    for (PriceRange priceRange: getPriceRangeListRequestResponse.priceRangeList) {
                        list.add(priceRange.priceRange);
                    }
                    selectPriceRangeDialog = new SelectPriceRangeDialog();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("priceRangeList", list);
                    selectPriceRangeDialog.setArguments(bundle);

                    // show saved state
                    for (PriceRange priceRange : getPriceRangeListRequestResponse.priceRangeList)
                    {
                        if (priceRange.priceMax.equals(params.price_max) && priceRange.priceMin.equals(params.price_min))
                        {
                            priceRangeSelectorBtn.setText(priceRange.priceRange);
                        }
                    }
                }
            }
        });
    }

    public void loadUserDistrictInfo()
    {
        UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
        getDistrictByCityNameRequestResponse = new GetDistrictByCityNameRequestResponse(userConfig.city, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == 0)
                {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add("全部");
                    for (District district: getDistrictByCityNameRequestResponse.districtList) {
                        list.add(district.name);
                        if (params.district.equals(district.name))
                        {
                            districtInfoSelectorBtn.setText(params.district);
                        }
                    }
                    selectDistrictDialog = new SelectDistrictDialog();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("districtList", list);
                    selectDistrictDialog.setArguments(bundle);




                }
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog == selectDayDialog)
        {
            RadioGroup radioGroup = selectDayDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            dayInfoSelectorBtn.setText(radioButton.getText());

            switch (radioGroup.indexOfChild(radioButton))
            {
                case 0:
                    params.day_eng = "all";
                    break;
                case 1:
                    params.day_eng = "monday";
                    break;
                case 2:
                    params.day_eng = "tuesday";
                    break;
                case 3:
                    params.day_eng = "wednesday";
                    break;
                case 4:
                    params.day_eng = "thursday";
                    break;
                case 5:
                    params.day_eng = "friday";
                    break;
                case 6:
                    params.day_eng = "saturday";
                    break;
                case 7:
                    params.day_eng = "sunday";
                    break;
            }
        }
        else if (dialog == selectTimeDialog)
        {
            RadioGroup radioGroup = selectTimeDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            timeInfoSelectorBtn.setText(radioButton.getText());
            switch (radioGroup.indexOfChild(radioButton))
            {
                case 0:
                    params.period_eng = "all";
                    break;
                case 1:
                    params.period_eng = "morning";
                    break;
                case 2:
                    params.period_eng = "afternoon";
                    break;
                case 3:
                    params.period_eng = "evening";
                    break;
            }
        }
        else if (dialog == selectDistrictDialog)
        {
            RadioGroup radioGroup = selectDistrictDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            districtInfoSelectorBtn.setText(radioButton.getText());
            int index = radioGroup.indexOfChild(radioButton);
            if (getDistrictByCityNameRequestResponse.districtList != null)
            {
                if (index == 0)
                {
                    params.district  = "all";
                }
                else
                {
                    District district = getDistrictByCityNameRequestResponse.districtList.get(index);
                    params.district  = district.name;
                }

            }
        }
        else if (dialog == selectPriceRangeDialog)
        {
            RadioGroup radioGroup = selectPriceRangeDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            priceRangeSelectorBtn.setText(radioButton.getText());
            int index = radioGroup.indexOfChild(radioButton);
            if (getPriceRangeListRequestResponse.priceRangeList != null)
            {
                if (index == 0)
                {
                    params.price_min  = "";
                    params.price_max = "";
                }
                else {
                    PriceRange priceRange = getPriceRangeListRequestResponse.priceRangeList.get(index);
                    params.price_min = priceRange.priceMin;
                    params.price_max = priceRange.priceMax;
                }
            }
        }
        getTutorListData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private String getSelectedCourse()
    {
        String text = "请选择";
        if (params.stage.equals(""))
        {
            text = "全部";
            params.course = "";
            params.subCourse = "";
        }
        else
        {
            if (params.course.equals(""))
            {
                text = params.stage;
                params.subCourse = "";
            }
            else
            {
                if (params.subCourse.equals("全部") || params.subCourse.equals(""))
                {
                    text = params.stage + "/" + params.course;
                    params.subCourse = "";
                }
                else
                {
                    text = params.stage + "/" + params.course + "/" + params.subCourse;
                }
            }
        }
        return text;
    }

    private void getTutorListData()
    {

        getTutorListRequestResponse.cancelPendingRequest();
        filterSubmitBtn.setVisibility(View.INVISIBLE);
        getTutorListRequestResponse = new GetTutorListRequestResponse(params,responseListener);
    }

    private void loadSavedState()
    {
        selectCourseButton.setText(getSelectedCourse());
        dayInfoSelectorBtn.setText(getDayInfoChinese(params.day_eng));
        timeInfoSelectorBtn.setText(getPeriodInfoChinese(params.period_eng));

        setSelectdButtonByGender(params.gender_eng);
        setSelectdButtonById(params.career);
        setSelectedButtonByOrder(params.order_by);

    }

    private void setSelectdButtonById(String career)
    {
        identityAllBtn.setSelected(false);
        identityServiceTeacherBtn.setSelected(false);
        identityCollegeStudentBtn.setSelected(false);
        identityParttimeTeacherBtn.setSelected(false);
        if (career.equals(""))
        {
            identityAllBtn.setSelected(true);
        }else if ( params.career.equals(identityServiceTeacherBtn.getText().toString()))
        {
            identityServiceTeacherBtn.setSelected(true);
        }else if ( params.career.equals(identityCollegeStudentBtn.getText().toString()))
        {
            identityCollegeStudentBtn.setSelected(true);
        }else if ( params.career.equals(identityParttimeTeacherBtn.getText().toString()))
        {
            identityParttimeTeacherBtn.setSelected(true);
        }

    }

    private void setSelectdButtonByGender(String genderEng)
    {
        genderAllBtn.setSelected(false);
        genderFemaleBtn.setSelected(false);
        genderMaleBtn.setSelected(false);

        Button selectedButton = genderAllBtn;
        switch (genderEng)
        {
            case "":
                selectedButton = genderAllBtn;
                break;
            case "female":
                selectedButton = genderFemaleBtn;
                break;
            case "male":
                selectedButton = genderMaleBtn;
                break;
        }
        selectedButton.setSelected(true);
    }

    private void setSelectedButtonByOrder(String order)
    {
        orderByDefaultBtn.setSelected(false);
        orderByRatingBtn.setSelected(false);
        orderByTotalClassTimeBtn.setSelected(false);
        orderByPriceBtn.setSelected(false);
        Button selectedButton = orderByDefaultBtn;
        switch (order)
        {
            case "general":
                selectedButton = orderByDefaultBtn;
                break;
            case "rating":
                selectedButton = orderByRatingBtn;
                break;
            case "total_class_time":
                selectedButton = orderByTotalClassTimeBtn;
                break;
            case "price":
                selectedButton = orderByPriceBtn;
                break;
        }
        selectedButton.setSelected(true);
    }
    private String getPeriodInfoChinese(String periodEng)
    {
        String text = "";
        switch (periodEng)
        {
            case "all":
                text = "任何时间";
                break;
            case "morning":
                text = "上午";
                break;
            case "afternoon":
                text = "下午";
                break;
            case "evening":
                text = "晚上";
                break;
        }
        return text;
    }

    private String getDayInfoChinese(String dayEng)
    {
        String text = "";
        switch (dayEng)
        {
            case "all":
                text = "任何时间";
                break;
            case "monday":
                text = "星期一";
                break;
            case "tuesday":
                text = "星期二";
                break;
            case "wednesday":
                text = "星期三";
                break;
            case "thursday":
                text = "星期四";
                break;
            case "friday":
                text = "星期五";
                break;
            case "saturday":
                text = "星期六";
                break;
            case "sunday":
                text = "星期日";
                break;

        }
        return text;
    }

}
