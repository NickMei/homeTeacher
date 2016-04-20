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
import com.kayluo.pokerface.api.auth.RegisterAccountRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.ui.base.BaseActivity;

public class CreateAccountActivity extends BaseActivity {

    private Toolbar mToolbar;
    private EditText passwordTextEdit;
    private EditText comfirmPasswordTextEdit;
    private Button submitButton;
    private RegisterAccountRequestResponse registerAccountRequestResponse;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        token = getIntent().getStringExtra("token");
        initToolBar();
        iniViews();
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.create_account_toolbar);
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

    private void iniViews()
    {
        passwordTextEdit = (EditText) findViewById(R.id.create_account_password);
        comfirmPasswordTextEdit = (EditText) findViewById(R.id.create_account_comfirm_password);
        submitButton = (Button) findViewById(R.id.create_account_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordTextEdit.getText().toString();
                String comfirmPassword = comfirmPasswordTextEdit.getText().toString();
                if (password.equals(comfirmPassword))
                {
                    registerAccountRequestResponse = new RegisterAccountRequestResponse(token,password, new RequestResponseBase.ResponseListener() {
                        @Override
                        public void onCompleted(ResponseInfo response) {
                            Toast.makeText(CreateAccountActivity.this, response.message, Toast.LENGTH_SHORT).show();
                            if (response.returnCode == 0)
                            {
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
                }

            }
        });

    }

}
