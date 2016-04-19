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

public class TeachingAddressActivity extends AppCompatActivity {

    public final static String INTENT_KEY_ADDRESS = "address";
    private Toolbar mToolbar;
    private EditText teachingAddressEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_address);
        initToolBar();
        setUpViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teaching_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.teaching_address_menu_item_edit)
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(INTENT_KEY_ADDRESS,teachingAddressEditText.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViews()
    {
        String teachingAddress = getIntent().getStringExtra(INTENT_KEY_ADDRESS);
        teachingAddressEditText = (EditText) findViewById(R.id.teaching_address_edit_text);
        teachingAddressEditText.setText(teachingAddress);
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.teaching_address_toolbar);
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
