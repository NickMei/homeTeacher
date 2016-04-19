package com.kayluo.pokerface.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kayluo.pokerface.R;

public class RemarksActivity extends AppCompatActivity {

    public final static String INTENT_KEY_MESSAGE = "message";
    private Toolbar mToolbar;
    private EditText remarksEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarks);
        initToolBar();
        setUpViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking_remarks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.booking_remarks_menu_item_edit)
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(INTENT_KEY_MESSAGE,remarksEditText.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViews()
    {
        String remarksText = getIntent().getStringExtra(INTENT_KEY_MESSAGE);
        remarksEditText = (EditText) findViewById(R.id.remarks_view_edit_text);
        remarksEditText.setText(remarksText);
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.remarks_view_toolbar);
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
}
