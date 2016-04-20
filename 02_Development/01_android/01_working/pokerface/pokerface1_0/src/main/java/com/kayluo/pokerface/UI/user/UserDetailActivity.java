package com.kayluo.pokerface.ui.user;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import com.kayluo.pokerface.ui.base.BaseActivity;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.api.studentCenter.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.studentCenter.SaveStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.UserBasicInfo;

public class UserDetailActivity extends BaseActivity {

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
    private View view;
    private Boolean editable = false;
    private ProgressDialog progressDialog;

    private GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        setContentView(R.layout.activity_user_detail);
        initToolBar();
        initView();
        progressDialog.show();
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
                progressDialog.show();
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("获取数据...");
        headPhotoImageView = (ImageView) this.findViewById(R.id.user_detail_head_photo);
        usernameEditText = (EditText) this.findViewById(R.id.user_detail_usrname);
        genderEditText = (EditText) this.findViewById(R.id.user_detail_gender);
        gradeSpinner = (Spinner) this.findViewById(R.id.user_detail_grade_spinner);
        citySpinner = (Spinner) this.findViewById(R.id.user_detail_city_spinner);
        gradeEditText = (EditText) this.findViewById(R.id.user_detail_grade);
        cityEditText = (EditText) this.findViewById(R.id.user_detail_city);
        preferredAddressEditText = (EditText) this.findViewById(R.id.user_detail_preferred_address);

        AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();

        gradeSpinnerAdapter = new ArrayAdapter<String>(UserDetailActivity.this, R.layout.spinner_item, appConfig.gradeList);
        gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeSpinnerAdapter);

        setSubviewsEditable(false);

        this.loadUserBasicInfo();

    }

    private void loadUserBasicInfo() {

        getStudentBasicInfoRequestResponse  = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo data) {
                UserBasicInfo basicInfo = getStudentBasicInfoRequestResponse.basicInfo;
                usernameEditText.setText(basicInfo.name);
                genderEditText.setText(basicInfo.gender);
                gradeEditText.setText(basicInfo.grade);
//                cityEditText.setText(basicInfo.city_name);
                preferredAddressEditText.setText(basicInfo.address);
                new BitmapDownloaderTask(headPhotoImageView).execute(basicInfo.head_photo);
                int selection = gradeSpinnerAdapter.getPosition(basicInfo.grade);
                gradeSpinner.setSelection(selection);
                progressDialog.dismiss();
            }
        });
    }
    private void saveUserBasicInfo()
    {
        UserBasicInfo basicInfo = new UserBasicInfo();
        basicInfo.name = usernameEditText.getText().toString() ;
        basicInfo.gender = genderEditText.getText().toString() ;
        basicInfo.grade = gradeSpinner.getSelectedItem().toString();
//        basicInfo.city_name = AppManager.shareInstance().settingManager.getUserConfig().profile.city.cityName;
        basicInfo.address = preferredAddressEditText.getText().toString() ;

        new SaveStudentBasicInfoRequestResponse(basicInfo, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo responseInfo) {

                Toast.makeText(UserDetailActivity.this, responseInfo.message, Toast.LENGTH_SHORT).show();
                if (responseInfo.returnCode == 0) {
                    loadUserBasicInfo();
                }
            }
        });
    }
}
