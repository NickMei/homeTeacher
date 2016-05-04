package com.kayluo.pokerface.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.tutorInfo.GetPriceRangeListRequestResponse;
import com.kayluo.pokerface.api.tutorInfo.GetTutorListRequestResponse;
import com.kayluo.pokerface.common.EActivityRequestCode;
import com.kayluo.pokerface.component.dialog.SingleChoiceListDialog;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.District;
import com.kayluo.pokerface.dataModel.PriceRange;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.Stage;
import com.kayluo.pokerface.dataModel.SubCourse;
import com.kayluo.pokerface.ui.tutor.TutorListViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchTabFragment extends Fragment{

    View v;
    Context mContext;

    GetTutorListRequestResponse.Params params;
    private RequestResponseBase.ResponseListener responseListener;

    private Toolbar mToolbar;
    private TextView selectCourseButton;
    private TextView datetimeInfoSelectorBtn;
    private TextView districtInfoSelectorBtn;
    private TextView priceRangeSelectorBtn;
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
    private Button identityPartTimeTeacherBtn;
    private Button filterSubmitBtn;

    private SingleChoiceListDialog selectTimeDialog;
    private SingleChoiceListDialog selectDayDialog;
    private SingleChoiceListDialog selectDistrictDialog;
    private SingleChoiceListDialog selectPriceRangeDialog;

    GetTutorListRequestResponse getTutorListRequestResponse;
    GetPriceRangeListRequestResponse getPriceRangeListRequestResponse;

    int stageIndex;
    int courseIndex;
    int subCourseIndex;

    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.tab_search, container, false);
        this.mContext = container.getContext();
        setUpViews();
        fm = getActivity().getSupportFragmentManager();
        params  = new GetTutorListRequestResponse().new Params();
        sendAPIRequest();
        loadSavedState();
        return v;
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

        selectCourseButton = (TextView) v.findViewById(R.id.tab_search_select_course_button);
        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,SelectCourseActivity.class);
                intent.putExtra("stageIndex",stageIndex);
                intent.putExtra("courseIndex",courseIndex);
                intent.putExtra("subCourseIndex",subCourseIndex);
                getActivity().startActivityForResult(intent, EActivityRequestCode.SELECT_COURSE.getValue());
            }
        });
        selectDayDialog = new SingleChoiceListDialog();
        selectTimeDialog= new SingleChoiceListDialog();

        String[] dayStringArray = getResources().getStringArray(R.array.day_list);
        ArrayList<String> dayList = new ArrayList<String>(Arrays.asList(dayStringArray));
        final Bundle selectDayDialogBundle = new Bundle();
        selectDayDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, dayList);
        selectDayDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择日期");
        selectDayDialog.setArguments(selectDayDialogBundle);

        String[] timeStringArray = getResources().getStringArray(R.array.time_list);
        ArrayList<String> timeList = new ArrayList<String>(Arrays.asList(timeStringArray));
        Bundle selectTimeDialogDialogBundle = new Bundle();
        selectTimeDialogDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, timeList);
        selectTimeDialogDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择时间段");
        selectTimeDialog.setArguments(selectTimeDialogDialogBundle);


        datetimeInfoSelectorBtn = (TextView) v.findViewById(R.id.tab_search_datetime_selector_button);
        datetimeInfoSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDayDialog.show(fm,"");
            }
        });

        districtInfoSelectorBtn = (TextView) v.findViewById(R.id.tab_search_district_selector_button);
        districtInfoSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSelectDistrictDialog();
                if (selectDistrictDialog != null) {
                    selectDistrictDialog.show(fm, "");
                }
            }
        });

        priceRangeSelectorBtn = (TextView) v.findViewById(R.id.tab_search_price_filter_button);
        priceRangeSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPriceRangeDialog.show(getFragmentManager(), "");
            }
        });

        orderByDefaultBtn = (Button) v.findViewById(R.id.tab_search_order_by_default);

        orderByRatingBtn = (Button) v.findViewById(R.id.tab_search_order_by_rating);
        orderByTotalClassTimeBtn = (Button) v.findViewById(R.id.tab_search_order_by_total_class_time);
        orderByPriceBtn = (Button) v.findViewById(R.id.tab_search_order_by_price_asc);

        genderAllBtn = (Button) v.findViewById(R.id.tab_search_gender_all);
        genderFemaleBtn = (Button) v.findViewById(R.id.tab_search_gender_female);
        genderMaleBtn = (Button) v.findViewById(R.id.tab_search_gender_male);

        identityAllBtn = (Button) v.findViewById(R.id.tab_search_identity_all);
        identityServiceTeacherBtn = (Button) v.findViewById(R.id.tab_search_identity_service_teacher);
        identityCollegeStudentBtn = (Button) v.findViewById(R.id.tab_search_identity_collage_student);
        identityPartTimeTeacherBtn = (Button) v.findViewById(R.id.tab_search_identity_parttime_teacher);

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
                identityPartTimeTeacherBtn.setSelected(false);
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
        identityPartTimeTeacherBtn.setOnClickListener(identityClickListener);

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

        filterSubmitBtn = (Button) v.findViewById(R.id.tab_search_submit_button);
        filterSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // save params in intent and nav to tutorlist view
                Intent intent = new Intent(mContext, TutorListViewActivity.class);
                String serializedString = new Gson().toJson(params);
                intent.putExtra("params", serializedString);
                intent.putExtra("stageIndex",stageIndex);
                intent.putExtra("courseIndex", courseIndex);
                intent.putExtra("subCourseIndex",subCourseIndex);
                getActivity().startActivityForResult(intent,EActivityRequestCode.TUTOR_LIST.getValue());
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
                    selectPriceRangeDialog = new SingleChoiceListDialog();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, list);
                    bundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择价格区间");
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

    public void createSelectDistrictDialog()
    {
        UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();

        if (userConfig.districtList == null)
        {
            Toast.makeText(mContext,"暂不支持当前地区",Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add("全部");
        for (District district: userConfig.districtList)
        {
            list.add(district.name);
        }
        selectDistrictDialog = new SingleChoiceListDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, list);
        bundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择地区");
        selectDistrictDialog.setArguments(bundle);
    }

    public void updateSelectedCourse(Intent data)
    {
            stageIndex = data.getIntExtra("stageIndex", 0);
            courseIndex = data.getIntExtra("courseIndex", 0);
            subCourseIndex = data.getIntExtra("subCourseIndex", 0);
            List<Stage> stageList = AppManager.shareInstance().settingManager.getAppConfig().stageList;
            params.stage = "";
            params.course = "";
            params.sub_course = "";
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
                        params.sub_course = subCourse.name;
                    }
                }
            }

            selectCourseButton.setText(getSelectedCourse());
            getTutorListData();


    }

    private String getSelectedCourse()
    {
        String text = "请选择";
        if (params.stage.equals(""))
        {
            text = "全部";
            params.course = "";
            params.sub_course = "";
        }
        else
        {
            if (params.course.equals(""))
            {
                text = params.stage;
                params.sub_course = "";
            }
            else
            {
                if (params.sub_course.equals("全部") || params.sub_course.equals(""))
                {
                    text = params.stage + "/" + params.course;
                    params.sub_course = "";
                }
                else
                {
                    text = params.stage + "/" + params.course + "/" + params.sub_course;
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
        datetimeInfoSelectorBtn.setText(getDayInfoChinese(params.day_eng) + "," + getPeriodInfoChinese(params.period_eng));
        if (!params.district.equals("")) {
            districtInfoSelectorBtn.setText(params.district);
        }
        setSelectdButtonByGender(params.gender_eng);
        setSelectdButtonById(params.career);
        setSelectedButtonByOrder(params.order_by);

    }

    private void setSelectdButtonById(String career)
    {
        identityAllBtn.setSelected(false);
        identityServiceTeacherBtn.setSelected(false);
        identityCollegeStudentBtn.setSelected(false);
        identityPartTimeTeacherBtn.setSelected(false);
        if (career.length() == 0)
        {
            identityAllBtn.setSelected(true);
        }else if ( params.career.equals(identityServiceTeacherBtn.getText().toString()))
        {
            identityServiceTeacherBtn.setSelected(true);
        }else if ( params.career.equals(identityCollegeStudentBtn.getText().toString()))
        {
            identityCollegeStudentBtn.setSelected(true);
        }else if ( params.career.equals(identityPartTimeTeacherBtn.getText().toString()))
        {
            identityPartTimeTeacherBtn.setSelected(true);
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
                text = "不限";
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
                text = "不限";
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

    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog) {
        if (dialog == selectDayDialog)
        {
            RadioGroup radioGroup = selectDayDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            datetimeInfoSelectorBtn.setText(radioButton.getText());
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
            selectTimeDialog.show(getFragmentManager(),"");
        }
        else if (dialog == selectTimeDialog)
        {
            RadioGroup radioGroup = selectTimeDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            String day = datetimeInfoSelectorBtn.getText().toString();
            datetimeInfoSelectorBtn.setText(day + ", " + radioButton.getText().toString());
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
            if (AppManager.shareInstance().settingManager.getUserConfig().districtList != null)
            {
                if (index == 0)
                {
                    params.district  = "";
                }
                else
                {
                    District district = AppManager.shareInstance().settingManager.getUserConfig().districtList.get(index - 1);
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

    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {

    }
}
