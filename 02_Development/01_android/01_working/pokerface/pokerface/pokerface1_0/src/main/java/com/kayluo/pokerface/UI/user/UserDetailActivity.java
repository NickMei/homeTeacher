package com.kayluo.pokerface.UI.user;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.kayluo.pokerface.R;
import com.kayluo.pokerface.Util.BitmapDownloaderTask;
import com.kayluo.pokerface.api.GetCityListRequestResponse;
import com.kayluo.pokerface.api.GetOpenCityListRequestResponse;
import com.kayluo.pokerface.api.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.api.GetStudentGradeListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.api.SaveStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    private Context context;
    private Toolbar mToolbar;
    private ImageView headPhotoImageView;
    private EditText usernameEditText;
    private EditText genderEditText;
    private Spinner gradeSpinner;
    private Spinner citySpinner;
    private EditText gradeEditText;
    private EditText cityEditText;
    private EditText preferredAddressEditText;
    private ArrayAdapter<String> gradeSpinnerAdapter;
    private ArrayAdapter<String> citySpinnerAdapter;
    private View view;
    private Boolean editable = false;

    private static List<String> gradeList = new ArrayList<String>();
    private static List<String> cityList = new ArrayList<String>();

    private GetStudentGradeListRequestResponse getStudentGradeListRequestResponse;
    private GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;
    private GetOpenCityListRequestResponse getOpenCityListRequestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        setContentView(R.layout.activity_user_detail);
        initToolBar();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_detail_menu_item_edit) {
            if (!editable)
            {
                this.setSubviewsEditable(true);
                item.setTitle("完成");
            }else
            {
                this.setSubviewsEditable(false);
                item.setTitle("修改");
                saveUserBasicInfo();
            }
            editable = !editable;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.user_detail_toolbar);
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setSubviewsEditable(Boolean editable)
    {
        int textGravity = editable ?  Gravity.START : Gravity.END;
        usernameEditText.setGravity(textGravity);
        genderEditText.setGravity(textGravity);
        gradeSpinner.setVisibility(editable ? View.VISIBLE : View.GONE);
        gradeEditText.setVisibility(editable ? View.GONE : View.VISIBLE);
        citySpinner.setVisibility(editable ? View.VISIBLE : View.GONE);
        cityEditText.setVisibility(editable ? View.GONE : View.VISIBLE);
        preferredAddressEditText.setGravity(textGravity);
        usernameEditText.setEnabled(editable);
        genderEditText.setEnabled(editable);
        gradeSpinner.setEnabled(editable);
        preferredAddressEditText.setEnabled(editable);

    }



    private void initView(){

        headPhotoImageView = (ImageView) this.findViewById(R.id.user_detail_head_photo);
        usernameEditText = (EditText) this.findViewById(R.id.user_detail_usrname);
        genderEditText = (EditText) this.findViewById(R.id.user_detail_gender);
        gradeSpinner = (Spinner) this.findViewById(R.id.user_detail_grade_spinner);
        citySpinner = (Spinner) this.findViewById(R.id.user_detail_city_spinner);
        gradeEditText = (EditText) this.findViewById(R.id.user_detail_grade);
        cityEditText = (EditText) this.findViewById(R.id.user_detail_city);
        preferredAddressEditText = (EditText) this.findViewById(R.id.user_detail_preferred_address);

        gradeSpinnerAdapter = new ArrayAdapter<String>(UserDetailActivity.this, R.layout.spinner_item, gradeList);
        gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeSpinnerAdapter);

        citySpinnerAdapter = new ArrayAdapter<String>(UserDetailActivity.this, R.layout.spinner_item, cityList);
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(citySpinnerAdapter);

        setSubviewsEditable(false);

        this.loadUserBasicInfo();

    }

    private void loadUserBasicInfo() {
        if (gradeList.size() == 0) {
            getStudentGradeListRequestResponse = new GetStudentGradeListRequestResponse(new RequestResponseBase.ResponseListener() {
                @Override
                public void onCompleted(Object response) {
                    ResponseInfo responseInfo = (ResponseInfo) response;
                    if (responseInfo.returnCode.equals("0")) {
                        gradeList = getStudentGradeListRequestResponse.grade_list;
                        gradeSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if (cityList.size() == 0)
        {
            getOpenCityListRequestResponse = new GetOpenCityListRequestResponse(new RequestResponseBase.ResponseListener() {
                @Override
                public void onCompleted(Object response) {
                    ResponseInfo responseInfo = (ResponseInfo) response;
                    if (responseInfo.returnCode.equals("0"))
                    {
                        cityList = getOpenCityListRequestResponse.city_list;
                        gradeSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        getStudentBasicInfoRequestResponse  = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                UserBasicInfo basicInfo = getStudentBasicInfoRequestResponse.basicInfo;
                usernameEditText.setText(basicInfo.name);
                genderEditText.setText(basicInfo.gender);
                gradeEditText.setText(basicInfo.grade);
                cityEditText.setText(basicInfo.city);
                preferredAddressEditText.setText(basicInfo.address);
                new BitmapDownloaderTask(headPhotoImageView).execute(basicInfo.head_photo);
                int selection = gradeSpinnerAdapter.getPosition(basicInfo.grade);
                gradeSpinner.setSelection(selection);
                int citySelection = citySpinnerAdapter.getPosition(basicInfo.city);
                citySpinner.setSelection(citySelection);
            }
        });
    }
    private void saveUserBasicInfo()
    {
        UserBasicInfo basicInfo = new UserBasicInfo();
        basicInfo.name = usernameEditText.getText().toString() ;
        basicInfo.gender = genderEditText.getText().toString() ;
        basicInfo.grade = gradeSpinner.getSelectedItem().toString();
        basicInfo.city = citySpinner.getSelectedItem().toString() ;
        basicInfo.address = preferredAddressEditText.getText().toString() ;

        SaveStudentBasicInfoRequestResponse saveStudentBasicInfoRequestResponse = new SaveStudentBasicInfoRequestResponse(basicInfo, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                ResponseInfo responseInfo = (ResponseInfo) data;
                if (responseInfo.returnCode.equals("0")) {
                    loadUserBasicInfo();
                    Toast.makeText(UserDetailActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserDetailActivity.this, responseInfo.message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
