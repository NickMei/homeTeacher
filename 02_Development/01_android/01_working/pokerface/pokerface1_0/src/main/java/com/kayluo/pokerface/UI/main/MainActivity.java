package com.kayluo.pokerface.ui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.GetAllCourseListRequestResponse;
import com.kayluo.pokerface.api.location.GetOpenCityListRequestResponse;
import com.kayluo.pokerface.api.studentCenter.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.api.studentCenter.GetStudentGradeListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.ActivityRequestCode;
import com.kayluo.pokerface.component.NoScrollViewPager;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.database.UserProfile;

public class MainActivity extends AppCompatActivity {
	private NoScrollViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;

	private ImageView mHomeIcon;
	private ImageView mEventIcon;
	private ImageView mSearchIcon;
	private ImageView mUserIcon;
	private LinearLayout mHomeLinearLayout;
	private LinearLayout mEventLinearLayout;
	private LinearLayout mSearchLinearLayout;
	private LinearLayout mUserLinearLayout;

//	private ImageView mTabline;
	GetStudentGradeListRequestResponse getStudentGradeListRequestResponse;
	GetOpenCityListRequestResponse getOpenCityListRequestResponse;
	GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;
	GetAllCourseListRequestResponse getAllCourseListRequestResponse;

	private View splashScreen;

	private AppConfig appConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			callAPIs();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}


	private void callAPIs() throws Throwable {

		appConfig = AppManager.shareInstance().settingManager.getAppConfig();

		if (appConfig.stageList != null)
		{
			pendingRequestsDidFinished();
			return;
		}

		final CountDownLatch requestCountDown = new CountDownLatch(4);
		getStudentBasicInfoRequestResponse = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo responseInfo) {
				if (responseInfo.returnCode == 0) {
					UserProfile userProfile = AppManager.shareInstance().settingManager.getUserConfig().profile;
					userProfile.city = getStudentBasicInfoRequestResponse.basicInfo.city;
					userProfile.name = getStudentBasicInfoRequestResponse.basicInfo.name;
					userProfile.head_photo = getStudentBasicInfoRequestResponse.basicInfo.head_photo;
				} else {
					AppManager.shareInstance().settingManager.getUserConfig().logout(MainActivity.this);
				}
				requestCountDown.countDown();
			}

		});


		getAllCourseListRequestResponse = new GetAllCourseListRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo response) {
				appConfig.stageList = getAllCourseListRequestResponse.stageList;
				requestCountDown.countDown();
			}

		});

		getStudentGradeListRequestResponse = new GetStudentGradeListRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo response) {
				if (response.returnCode == 0) {
					AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
					appConfig.gradeList = getStudentGradeListRequestResponse.grade_list;
				}
				requestCountDown.countDown();
			}
		});


		getOpenCityListRequestResponse = new GetOpenCityListRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo response) {
				if (response.returnCode == 0) {
					AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
					appConfig.cityList = getOpenCityListRequestResponse.cityList;
				}
				requestCountDown.countDown();
			}
		});


		final Handler mainHandler = new Handler(this.getMainLooper());
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					requestCountDown.await();
					mainHandler.post(new Runnable() {
						@Override
						public void run() {
							pendingRequestsDidFinished();
						}

					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void pendingRequestsDidFinished() {
//		initTabLine();
		initView();
		setIconClickListener();
		splashScreen = findViewById(R.id.splash_screen);
		splashScreen.setVisibility(View.GONE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ActivityRequestCode.DISPLAY_LOGIN) {
			if (resultCode == RESULT_OK) {
				UserTabFragment tabUser = (UserTabFragment) mDatas.get(3);
				tabUser.loadData();
			}
		} else if (requestCode == ActivityRequestCode.SELECT_LOCATION) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				HomeTabFragment tabHome = (HomeTabFragment) mDatas.get(0);
				UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
				AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
				if (userConfig.isSignedIn)
				{
					userConfig.profile.city = appConfig.locationCity;
					userConfig.saveToStorage(this);
				}
				tabHome.setLocation(appConfig.locationCity);
			}
		}
		else if (requestCode ==  ActivityRequestCode.SETTINGS)
		{
			if (resultCode == RESULT_OK)
			{
				Boolean isSignedOut = data.getBooleanExtra("signOut",false);
				if (isSignedOut)
				{
					UserTabFragment tabUser = (UserTabFragment) mDatas.get(3);
					tabUser.loadData();
				}

			}
		}
	}


//	private void initTabLine() {
//		mTabline = (ImageView) findViewById(R.id.id_iv_tabline);
//		Display display = getWindow().getWindowManager().getDefaultDisplay();
//		DisplayMetrics outMetrics = new DisplayMetrics();
//		display.getMetrics(outMetrics);
//		mScreen1_4 = outMetrics.widthPixels / 4;
//		LayoutParams lp = mTabline.getLayoutParams();
//		lp.width = mScreen1_4;
//		mTabline.setLayoutParams(lp);
//	}

	private void initView() {

		mViewPager = (NoScrollViewPager) findViewById(R.id.id_viewpager);
		mViewPager.setNoScroll(true);
		mViewPager.setOffscreenPageLimit(4);
		mHomeIcon = (ImageView) findViewById(R.id.id_icon_home);
		mEventIcon = (ImageView) findViewById(R.id.id_icon_event);
		mSearchIcon = (ImageView) findViewById(R.id.id_icon_search);
		mUserIcon = (ImageView) findViewById(R.id.id_icon_user);
		mHomeLinearLayout = (LinearLayout) findViewById(R.id.id_ll_home);
		mEventLinearLayout = (LinearLayout) findViewById(R.id.id_ll_event);
		mSearchLinearLayout = (LinearLayout) findViewById(R.id.id_ll_search);
		mUserLinearLayout = (LinearLayout) findViewById(R.id.id_ll_user);

		mDatas = new ArrayList<Fragment>();

		HomeTabFragment tabHome = new HomeTabFragment();
		EventTabFragment tabEvent = new EventTabFragment();
		SearchTabFragment tabSearch = new SearchTabFragment();
		UserTabFragment tabUser = new UserTabFragment();


		mDatas.add(tabHome);
		mDatas.add(tabEvent);
		mDatas.add(tabSearch);
		mDatas.add(tabUser);


		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mDatas.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mDatas.get(arg0);
			}

		};
		mViewPager.setAdapter(mAdapter);

		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				resetImageView();
				switch (position) {
					case 0:
						mHomeIcon.setImageResource(R.drawable.history_blue_96);
						break;
					case 1:
						mEventIcon.setImageResource(R.drawable.topic_blue_96);
						break;
					case 2:
						mSearchIcon.setImageResource(R.drawable.find_user2_blue_96);
						break;
					case 3:
						mUserIcon.setImageResource(R.drawable.user_male_circle_filled_blue_96);
						break;

				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPx) {
//				Log.e("TAG", position + " , " + positionOffset + " , "
//						+ positionOffsetPx);
//
//				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabline
//						.getLayoutParams();
//
//				if (mCurrentPageIndex == 0 && position == 0)// 0->1
//				{
//					lp.leftMargin = (int) (positionOffset * mScreen1_4 + mCurrentPageIndex
//							* mScreen1_4);
//				} else if (mCurrentPageIndex == 1 && position == 0)// 1->0
//				{
//					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + (positionOffset - 1)
//							* mScreen1_4);
//				} else if (mCurrentPageIndex == 1 && position == 1) // 1->2
//				{
//					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + positionOffset
//							* mScreen1_4);
//				} else if (mCurrentPageIndex == 2 && position == 1) // 2->1
//				{
//					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + (positionOffset - 1)
//							* mScreen1_4);
//				} else if (mCurrentPageIndex == 2 && position == 2) // 2->3
//				{
//					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + positionOffset
//							* mScreen1_4);
//				} else if (mCurrentPageIndex == 3 && position == 2) // 3->2
//				{
//					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + (positionOffset - 1)
//							* mScreen1_4);
//				}
//				mTabline.setLayoutParams(lp);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	protected void setIconClickListener() {

		mHomeLinearLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mViewPager.setCurrentItem(0, false);
			}
		});

		mEventLinearLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mViewPager.setCurrentItem(1, false);
			}
		});

		mSearchLinearLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mViewPager.setCurrentItem(2, false);
			}
		});

		mUserLinearLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mViewPager.setCurrentItem(3, false);
			}
		});

	}


	public void setCurrentPage(int pageIndex) {
		mViewPager.setCurrentItem(pageIndex, true);
	}


	protected void resetImageView() {
		mHomeIcon.setImageResource(R.drawable.history_grey_96);
		mEventIcon.setImageResource(R.drawable.topic_grey_96);
		mSearchIcon.setImageResource(R.drawable.find_user2_grey_96);
		mUserIcon.setImageResource(R.drawable.user_male_circle_grey_96);

	}

}
