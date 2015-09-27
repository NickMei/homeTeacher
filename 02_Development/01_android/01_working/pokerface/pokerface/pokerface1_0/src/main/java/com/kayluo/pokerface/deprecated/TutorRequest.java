package com.kayluo.pokerface.deprecated;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.main.MainActivity;
import com.kayluo.pokerface.UI.StudentInfoFragment;

public class TutorRequest extends FragmentActivity
{
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;

    private ImageView mStudentIcon;
    private ImageView mTutorIcon;

	private ImageView mBackIcon;
	private ImageView mSubmitIcon;

	private LinearLayout mStudentLinearLayout;
    private LinearLayout mTutorLinearLayout;

	private ImageView mTabline;
	private int mScreen1_2;

	private int mCurrentPageIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.tutor_request_deprecated);

	    initTabLine();
		initView();
        setIconClickListener();
	}

	private void initTabLine()
	{
//		mTabline = (ImageView) findViewById(R.id.tutorRequest_tabLine);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_2 = outMetrics.widthPixels / 2;
		LayoutParams lp = mTabline.getLayoutParams();
		lp.width = mScreen1_2;
		mTabline.setLayoutParams(lp);
	}

private void initView()
	{
//		mViewPager = (ViewPager) findViewById(R.id.tutorRequestViewPager);
//
//        mStudentIcon = (ImageView) findViewById(R.id.icon_student);
//        mTutorIcon = (ImageView) findViewById(R.id.icon_tutor);
//
//        mBackIcon = (ImageView) findViewById(R.id.back_icon);
//
//		mStudentLinearLayout = (LinearLayout) findViewById(R.id.tutorRequest_ll_student);
//        mTutorLinearLayout = (LinearLayout) findViewById(R.id.tutorRequest_ll_tutor);

		mDatas = new ArrayList<Fragment>();

		StudentInfoFragment tabStudent = new StudentInfoFragment();
//        TutorInfoFragment tabTutor = new TutorInfoFragment();

		mDatas.add(tabStudent);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return mDatas.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mDatas.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				resetImageView();
				switch (position)
				{
				case 0:
                    mStudentIcon.setImageResource(R.drawable.student2_blue_96);
					break;
				case 1:
                    mTutorIcon.setImageResource(R.drawable.group_blue_96);
					break;
				}
				mCurrentPageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx)
			{
				Log.e("TAG", position + " , " + positionOffset + " , "
						+ positionOffsetPx);

				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabline
						.getLayoutParams();

				if (mCurrentPageIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (positionOffset * mScreen1_2 + mCurrentPageIndex
							* mScreen1_2);
				} else if (mCurrentPageIndex == 1 && position == 0)// 1->0
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_2 + (positionOffset - 1)
							* mScreen1_2);
				}
				mTabline.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
			}
		});

	}

    protected void setIconClickListener() {

        mStudentLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        mTutorLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });

        mBackIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

	protected void resetImageView()
	{
        mStudentIcon.setImageResource(R.drawable.student2_grey_96);
        mTutorIcon.setImageResource(R.drawable.group_grey_96);
	}

    public void backMainActivity() {
        Intent intent = new Intent();
        intent.setClass(TutorRequest.this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onBackPressed() {
		Intent myIntent = new Intent(TutorRequest.this, MainActivity.class);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
		startActivity(myIntent);
		finish();
		return;
	}

}
