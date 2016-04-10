package com.kayluo.pokerface.ui.user.commentRecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.studentCenter.GetCommentListRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class CommentRecordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private GetCommentListRequestResponse.TargetType targetType;
    private String tutorID;

    private List<CommentRecordActivityFragment> mDatas;
    private String tabTitles[] = new String[] { "全部", "好评", "中评","差评" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_record);
        String typeString = getIntent().getStringExtra("targetType");
        targetType = GetCommentListRequestResponse.TargetType.valueOf(typeString);
        tutorID =  getIntent().getStringExtra("tutorID");
        initToolBar();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_record, menu);
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

        mToolbar = (Toolbar) findViewById(R.id.comment_record_toolbar);
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
        mDatas = new ArrayList<CommentRecordActivityFragment>();
        for (int index = 0; index < tabTitles.length; index ++)
        {
            mDatas.add(CommentRecordActivityFragment.newInstance(index,targetType,tutorID));
        }
        tabLayout = (TabLayout) findViewById(R.id.comment_record_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.comment_record_viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                CommentRecordActivity.this));
        tabLayout.setupWithViewPager(viewPager);


    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

}
