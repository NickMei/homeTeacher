package com.kayluo.pokerface.ui.user;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.ui.user.Register.RegisterActivity;
import com.kayluo.pokerface.api.auth.LogoutRequestResponse;
import com.kayluo.pokerface.common.ActivityRequestCode;
import com.kayluo.pokerface.component.ChangePasswordDialog;
import com.kayluo.pokerface.util.OnDialogButtonClickListener;
import com.kayluo.pokerface.api.auth.ModifyPasswordRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;

public class SettingsActivity extends AppCompatActivity implements OnDialogButtonClickListener {

    private Toolbar mToolbar;
    private View changePasswordView;
    private View changePhoneNumView;
    private TextView phoneNumTextView;
    private LinearLayout logoutView;
    private ModifyPasswordRequestResponse changePasswordRequestResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolBar();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
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

    private void initViews()
    {
        Boolean isMemberSignedIn = AppManager.shareInstance().settingManager.getUserConfig().isSignedIn;
        logoutView = (LinearLayout) findViewById(R.id.tab_user_logout);
        logoutView.setVisibility(isMemberSignedIn?View.VISIBLE : View.GONE);
        phoneNumTextView = (TextView) findViewById(R.id.settings_phone_num);
        phoneNumTextView.setText(AppManager.shareInstance().settingManager.getUserConfig().mobile);
        changePhoneNumView = findViewById(R.id.change_phone_num_view);
        changePhoneNumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, RegisterActivity.class);
                intent.putExtra("createNewAccount",false);
                SettingsActivity.this.startActivityForResult(intent, ActivityRequestCode.CHANGE_PASSWORD);
            }
        });

        changePasswordView = findViewById(R.id.change_password_view);
        changePasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog dialog = new ChangePasswordDialog();
                dialog.show(getFragmentManager(),"changePassword");
            }
        });


        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.shareInstance().settingManager.getUserConfig().logout(SettingsActivity.this);
                LogoutRequestResponse logoutRequest = new LogoutRequestResponse(new RequestResponseBase.ResponseListener() {
                    @Override
                    public void onCompleted(ResponseInfo data) {
                        Toast.makeText(SettingsActivity.this, "登出成功", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("signOut",true);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }

                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityRequestCode.CHANGE_PASSWORD)
        {
            if (requestCode == RESULT_OK)
            {
                UserConfig config = AppManager.shareInstance().settingManager.getUserConfig();
                phoneNumTextView.setText(config.mobile);
            }
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog instanceof ChangePasswordDialog)
        {
            String oldPassword = ((ChangePasswordDialog) dialog).oldPassword.getText().toString();
            String newPassword = ((ChangePasswordDialog) dialog).newPassword.getText().toString();
            changePasswordRequestResponse = new ModifyPasswordRequestResponse(oldPassword, newPassword, new RequestResponseBase.ResponseListener() {
                @Override
                public void onCompleted(ResponseInfo response) {
                    if (response.returnCode == 0)
                    {
                        UserConfig config = AppManager.shareInstance().settingManager.getUserConfig();
                        config.token = changePasswordRequestResponse.token;
                        config.saveToStorage(SettingsActivity.this);
                        Toast.makeText(SettingsActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
