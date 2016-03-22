package com.kayluo.pokerface.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.ui.user.Register.RegisterActivity;
import com.kayluo.pokerface.api.auth.LoginRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;

public class LoginViewActivity extends AppCompatActivity {



    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Toolbar mToolbar;
    private TextView regsisterTextView;
    private ProgressDialog mProgressDialog;

    //API request
    private LoginRequestResponse authRequestResponse;

    final static int CREATE_ACCOUNT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        regsisterTextView = (TextView) findViewById(R.id.register_text_view);
        regsisterTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginViewActivity.this, RegisterActivity.class);
                intent.putExtra("createNewAccount",true);
                LoginViewActivity.this.startActivityForResult(intent,CREATE_ACCOUNT);
            }
        });

        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("获取数据...");
        mProgressDialog.setCancelable(false);
        this.initToolBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_ACCOUNT)
        {
            if (requestCode == RESULT_OK)
            {
//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
//                finish();
            }
        }
    }


    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.login_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED,returnIntent);
                finish();
            }
        });

    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (authRequestResponse != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authRequestResponse = new LoginRequestResponse(email, password, new RequestResponseBase.ResponseListener() {
                @Override
                public void onCompleted(ResponseInfo responseInfo) {
                    showProgress(false);
                    if (responseInfo.returnCode == 0) {
                        UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
                        userConfig.isSignedIn = true;
                        userConfig.mobile = authRequestResponse.mobile;
                        userConfig.token = authRequestResponse.token;
                        userConfig.userId = authRequestResponse.user_id;
                        userConfig.saveToStorage(LoginViewActivity.this);
                        Toast.makeText(LoginViewActivity.this, responseInfo.message, Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        mPasswordView.setError(responseInfo.message);
                        mPasswordView.requestFocus();
                        authRequestResponse = null;
                    }
                }

            });

        }
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 0;
    }


    public void showProgress(final boolean show) {

        if (show){
            mProgressDialog.show();
        }else
        {
            mProgressDialog.hide();
        }

    }






}

