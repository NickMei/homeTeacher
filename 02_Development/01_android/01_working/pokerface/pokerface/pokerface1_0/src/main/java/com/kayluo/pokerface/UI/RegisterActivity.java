package com.kayluo.pokerface.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.kayluo.pokerface.R;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
    }


}
