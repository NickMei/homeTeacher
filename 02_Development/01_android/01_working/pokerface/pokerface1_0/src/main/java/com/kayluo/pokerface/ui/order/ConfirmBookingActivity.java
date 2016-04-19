package com.kayluo.pokerface.ui.order;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.order.AddOrderRequestResponse;
import com.kayluo.pokerface.api.order.GetOrderTutorInfoRequestResponse;
import com.kayluo.pokerface.common.EActivityRequestCode;
import com.kayluo.pokerface.common.EReturnCode;
import com.kayluo.pokerface.component.dialog.SingleChoiceListDialog;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TeachInfo;
import com.kayluo.pokerface.dataModel.Order;
import com.kayluo.pokerface.component.dialog.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.Arrays;


public class ConfirmBookingActivity extends AppCompatActivity implements OnDialogButtonClickListener {

    private Toolbar mToolbar;
    private String tutorId;
    private int selectedCourseIndex;
    private int selectedTeachingMethodIndex;
    private int selectedTeachingDayIndex;
    private int selectedTeachingTimeIndex;

    private LinearLayout remarksView;
    private LinearLayout selectTeachingMethodView;
    private LinearLayout selectCourseNameView;
    private LinearLayout selectTeachingDatetimeView;
    private LinearLayout selectTeachingAddressView;

    private TextView studentNameTextView;
    private TextView courseNameTextView;
    private TextView teachingMethodTextView;
    private TextView teachingTimeTextView;
    private TextView teachingAddressTextView;
    private TextView bookingRemarksTextView;

    private GetOrderTutorInfoRequestResponse getOrderTutorInfoRequest;
    private AddOrderRequestResponse addOrderRequestResponse;
    private Order order;

    private SingleChoiceListDialog selectBookingCourseDialog;
    private SingleChoiceListDialog selectTeachingMethodDialog;
    private SingleChoiceListDialog selectTeachingDayDialog;
    private SingleChoiceListDialog selectTeachingTimeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        tutorId = getIntent().getStringExtra("tutorID");
        initToolBar();
        apiRequest();
        setUpViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EActivityRequestCode code = EActivityRequestCode.fromOrdinal(requestCode);
        switch (code)
        {
            case BOOKING_REMAKRS:
            {
                if(resultCode == RESULT_OK)
                {
                    order.extraInfo = data.getStringExtra(RemarksActivity.INTENT_KEY_MESSAGE);
                    bookingRemarksTextView.setText(order.extraInfo);
                    bookingRemarksTextView.setVisibility(order.extraInfo.equals("")? View.GONE:View.VISIBLE);
                }
                break;

            }
            case TEACHING_ADDRESS:
            {
                if(resultCode == RESULT_OK) {
                    order.classAddress = data.getStringExtra(TeachingAddressActivity.INTENT_KEY_ADDRESS);
                    teachingAddressTextView.setText(order.classAddress);
                    teachingAddressTextView.setVisibility(order.classAddress.equals("") ? View.GONE : View.VISIBLE);
                }
                break;
            }
        }

    }

    private void setUpViews() {

        studentNameTextView = (TextView) findViewById(R.id.confirm_booking_student_name_text_view);
        courseNameTextView = (TextView) findViewById(R.id.confirm_booking_course_name_text_view);
        teachingMethodTextView = (TextView) findViewById(R.id.confirm_booking_teaching_method_text_view);
        teachingTimeTextView = (TextView) findViewById(R.id.confirm_booking_teaching_time_text_view);
        teachingAddressTextView = (TextView) findViewById(R.id.confirm_booking_teaching_address_text_view);
        bookingRemarksTextView = (TextView) findViewById(R.id.confirm_booking_remarks_text_view);


        selectCourseNameView = (LinearLayout) findViewById(R.id.confirm_booking_select_course_name_view);
        selectCourseNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBookingCourseDialog.show(getFragmentManager(),"");
            }
        });

        selectTeachingMethodView = (LinearLayout) findViewById(R.id.confirm_booking_select_teaching_method_view);
        selectTeachingMethodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTeachingMethodDialog.show(getFragmentManager(),"");
            }
        });

        selectTeachingAddressView = (LinearLayout) findViewById(R.id.confirm_booking_select_teaching_address_view);
        selectTeachingAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmBookingActivity.this,TeachingAddressActivity.class);
                intent.putExtra(TeachingAddressActivity.INTENT_KEY_ADDRESS,order.classAddress);
                startActivityForResult(intent, EActivityRequestCode.TEACHING_ADDRESS.getValue());
            }
        });

        selectTeachingDatetimeView = (LinearLayout) findViewById(R.id.confirm_booking_select_teaching_datetime_view);
        selectTeachingDatetimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTeachingDayDialog.show(getFragmentManager(),"");
            }
        });

        remarksView = (LinearLayout) findViewById(R.id.confirm_booking_remarks_view);
        remarksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmBookingActivity.this,RemarksActivity.class);
                intent.putExtra(RemarksActivity.INTENT_KEY_MESSAGE,order.extraInfo);
                startActivityForResult(intent, EActivityRequestCode.BOOKING_REMAKRS.getValue());
            }
        });
    }

    private void apiRequest()
    {
        order = new Order();
        getOrderTutorInfoRequest =  new GetOrderTutorInfoRequestResponse(tutorId, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == EReturnCode.SUCCESS.getValue())
                {
                    createOrderInfo();
                    createDialogs();
                }
            }
        });
    }

    private void createOrderInfo()
    {
//        order.courseId = getOrderTutorInfoRequest.teachInfo.teachInfo
        order.studentId = AppManager.shareInstance().settingManager.getUserConfig().userId;
        order.tutorId = tutorId;
        order.studentName =  getOrderTutorInfoRequest.studentBasicInfo.name;
        studentNameTextView.setText(order.studentName);

    }

    private void createDialogs()
    {
        selectBookingCourseDialog =  new SingleChoiceListDialog();
        selectTeachingMethodDialog =  new SingleChoiceListDialog();
        selectTeachingDayDialog = new SingleChoiceListDialog();
        selectTeachingTimeDialog = new SingleChoiceListDialog();

        ArrayList<String> courseList = new ArrayList<String>();
        for (TeachInfo.CourseInfo courseInfo : getOrderTutorInfoRequest.teachInfo.courseList)
        {
            courseList.add(courseInfo.courseStr);
        }
        Bundle selectBookingCourseDialogBundle = new Bundle();
        selectBookingCourseDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, courseList);
        selectBookingCourseDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择科目");
        selectBookingCourseDialog.setArguments(selectBookingCourseDialogBundle);


        ArrayList<String> teachingMethodList = new ArrayList<String>();
        for (TeachInfo.TeachingMethod teachingMethod : getOrderTutorInfoRequest.teachInfo.methodList)
        {
            teachingMethodList.add(teachingMethod.MethodName);
        }
        Bundle selectTeachingMethodDialogBundle = new Bundle();
        selectTeachingMethodDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, teachingMethodList);
        selectTeachingMethodDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择授课方式");
        selectTeachingMethodDialog.setArguments(selectTeachingMethodDialogBundle);

        String[] dayStringArray = getResources().getStringArray(R.array.day_list);
        ArrayList<String> dayList = new ArrayList<String>(Arrays.asList(dayStringArray));
        Bundle selectTeachingDayDialogBundle = new Bundle();
        selectTeachingDayDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, dayList);
        selectTeachingDayDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择日期");
        selectTeachingDayDialog.setArguments(selectTeachingDayDialogBundle);

        String[] timeStringArray = getResources().getStringArray(R.array.time_list);
        ArrayList<String> timeList = new ArrayList<String>(Arrays.asList(timeStringArray));
        Bundle selectTeachingTimeDialogDialogBundle = new Bundle();
        selectTeachingTimeDialogDialogBundle.putStringArrayList(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_ARRAY, timeList);
        selectTeachingTimeDialogDialogBundle.putString(SingleChoiceListDialog.DIALOG_BUNDLE_KEY_TITLE, "选择时间段");
        selectTeachingTimeDialog.setArguments(selectTeachingTimeDialogDialogBundle);

    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.confirm_booking_view_toolbar);
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog == selectBookingCourseDialog)
        {
            RadioGroup radioGroup = selectBookingCourseDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            courseNameTextView.setText(radioButton.getText());
            selectedCourseIndex = radioGroup.indexOfChild(radioButton);
        }else if (dialog == selectTeachingMethodDialog)
        {
            RadioGroup radioGroup = selectTeachingMethodDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            teachingMethodTextView.setText(radioButton.getText());
            selectedTeachingMethodIndex = radioGroup.indexOfChild(radioButton);

        }else if (dialog == selectTeachingDayDialog)
        {
            RadioGroup radioGroup = selectTeachingDayDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            teachingTimeTextView.setText(radioButton.getText());
            selectedTeachingDayIndex = radioGroup.indexOfChild(radioButton);

            selectTeachingTimeDialog.show(getFragmentManager(),"");
        }else if (dialog == selectTeachingTimeDialog)
        {
            RadioGroup radioGroup = selectTeachingTimeDialog.radioGroup;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            String text = teachingTimeTextView.getText().toString();
            teachingTimeTextView.setText(text + ", " + radioButton.getText());
            selectedTeachingTimeIndex = radioGroup.indexOfChild(radioButton);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
