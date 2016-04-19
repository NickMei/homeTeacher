package com.kayluo.pokerface.ui.user.register;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.auth.AuthMsgCodeRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.SendVerifyMessageRequestResponse;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;


public class RegisterActivity extends AppCompatActivity {

    private Boolean createNewAccount;
    private Toolbar mToolbar;
    private Button sendVerifyMsgButton;
    private EditText phoneTextEdit;
    private EditText verifyCodeTextEdit;
    private Button submitButton;
    private SendVerifyMessageRequestResponse sendVerifyMessageRequestResponse;
    private AuthMsgCodeRequestResponse authMsgCodeRequestResponse;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createNewAccount = getIntent().getBooleanExtra("createNewAccount",false);
        initToolBar();
        initViews();
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    final static int CREATE_ACCOUNT = 1;
    private void initViews()
    {
        verifyCodeTextEdit = (EditText) findViewById(R.id.verCode);
        phoneTextEdit = (EditText) findViewById(R.id.phone_num);
        sendVerifyMsgButton = (Button) findViewById(R.id.send_verify_msg_button);
        sendVerifyMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = phoneTextEdit.getText().toString();
                sendVerifyMessageRequestResponse = new SendVerifyMessageRequestResponse(createNewAccount, mobile, new RequestResponseBase.ResponseListener() {
                    @Override
                    public void onCompleted(ResponseInfo response) {
                        Toast.makeText(RegisterActivity.this,response.message,Toast.LENGTH_SHORT).show();
                        if (response.returnCode == 0) {
                            verifyCodeTextEdit.setText(sendVerifyMessageRequestResponse.msgcode);
                        }
                    }
                });
            }
        });
        submitButton = (Button) findViewById(R.id.register_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = phoneTextEdit.getText().toString();
                String verifyCode = verifyCodeTextEdit.getText().toString();
                authMsgCodeRequestResponse = new AuthMsgCodeRequestResponse(createNewAccount, mobile, verifyCode, new RequestResponseBase.ResponseListener() {
                    @Override
                    public void onCompleted(ResponseInfo response) {
                        Toast.makeText(RegisterActivity.this,response.message,Toast.LENGTH_SHORT).show();
                        if (response.returnCode == 0)
                        {
                            UserConfig config = AppManager.shareInstance().settingManager.getUserConfig();
                            config.profile.mobile = mobile;
                            config.saveToStorage(RegisterActivity.this);
                            if(createNewAccount)
                            {
                                token = authMsgCodeRequestResponse.token;
//                                config.saveToStorage(RegisterActivity.this);
                                Intent intent = new Intent(RegisterActivity.this,CreateAccountActivity.class);
                                intent.putExtra("token",token);
                                RegisterActivity.this.startActivityForResult(intent,CREATE_ACCOUNT);
                            }else{
                                setResult(RESULT_OK);
                                finish();
                            }

                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_ACCOUNT)
        {
            setResult(resultCode,data);
            finish();
        }
    }
}
