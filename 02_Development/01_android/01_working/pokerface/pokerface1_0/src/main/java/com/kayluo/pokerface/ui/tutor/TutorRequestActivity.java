package com.kayluo.pokerface.ui.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.ui.base.BaseActivity;

public class TutorRequestActivity extends BaseActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_request);
        initToolBar();
    }

    private void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.tutor_request_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result");
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }


}
