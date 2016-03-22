package com.kayluo.pokerface.ui.user.TransactionRecord;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kayluo.pokerface.R;

import java.util.ArrayList;
import java.util.List;

public class UserTransactionRecordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int pageIndex;

    private List<TransactionRecordFragment> mDatas;
    private String tabTitles[] = new String[] { "全部", "待支付", "进行中","已完成","已取消" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transaction_record);
        initToolBar();
        initViews();
        pageIndex = getIntent().getIntExtra("index",0);
        viewPager.setCurrentItem(pageIndex);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_transaction_record, menu);
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

        mToolbar = (Toolbar) findViewById(R.id.user_transaction_record_toolbar);
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
        mDatas = new ArrayList<TransactionRecordFragment>();
        for (int index = 0; index < tabTitles.length; index ++)
        {
            mDatas.add(TransactionRecordFragment.newInstance(index));
        }
        tabLayout = (TabLayout) findViewById(R.id.user_transaction_record_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.user_transaction_viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                UserTransactionRecordActivity.this));
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
