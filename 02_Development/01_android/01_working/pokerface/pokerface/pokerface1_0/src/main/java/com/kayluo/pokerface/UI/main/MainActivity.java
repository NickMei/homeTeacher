package com.kayluo.pokerface.UI.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;

import com.jauker.widget.BadgeView;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.LocationCityListViewActivity;
import com.kayluo.pokerface.UI.TutorRequestActivity;
import com.kayluo.pokerface.UI.user.LoginViewActivity;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.GPSTracker;
import com.kayluo.pokerface.core.RequestTask;

public class MainActivity extends AppCompatActivity
{
	private ViewPager mViewPager;
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

	private BadgeView mBadgeView;

	private ImageView mTabline;
	private int mScreen1_4;

	private int mCurrentPageIndex;



	private Button locationButton;
	private Context mContext;

	static final int PICK_CONTACT_REQUEST = 1;  // The request code
	static final int DISPLAY_LOGIN = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);

		initTabLine();
		initView();
        initActionBar();
        setIconClickListener();
	}

	private void initActionBar() {
		// Initialize the Toolbar( this is the new ActionBar in Lollipop)
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		locationButton = (Button) toolbar.findViewById(R.id.locationButton);
		locationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, LocationCityListViewActivity.class);
				startActivityForResult(intent, PICK_CONTACT_REQUEST);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == PICK_CONTACT_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				String locationName = data.getStringExtra("location");
				locationButton.setText(locationName);
				// The user picked a contact.
				// The Intent's data Uri identifies which contact was selected.

				// Do something with the contact here (bigger example below)
			}
		}else if(requestCode == DISPLAY_LOGIN)
		{
			if (resultCode == RESULT_OK) {
				setCurrentPage(3);
				UserTabFragment tabUser = (UserTabFragment) mDatas.get(3);
				tabUser.loadData();
			}
		}
	}

	public void hideLocationButton(){

		locationButton.setVisibility(View.GONE);
	}

	public void showLocationButton() {
		locationButton.setVisibility(View.VISIBLE);
	}


		private void initTabLine()
	{
		mTabline = (ImageView) findViewById(R.id.id_iv_tabline);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_4 = outMetrics.widthPixels / 4;
		LayoutParams lp = mTabline.getLayoutParams();
		lp.width = mScreen1_4;
		mTabline.setLayoutParams(lp);
	}

	private void initView()
	{

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
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

        //checkIsUserSignedIn(false);


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
					lp.leftMargin = (int) (positionOffset * mScreen1_4 + mCurrentPageIndex
							* mScreen1_4);
				} else if (mCurrentPageIndex == 1 && position == 0)// 1->0
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + (positionOffset - 1)
							* mScreen1_4);
				} else if (mCurrentPageIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + positionOffset
							* mScreen1_4);
				} else if (mCurrentPageIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + ( positionOffset-1)
							* mScreen1_4);
				}else if (mCurrentPageIndex == 2 && position == 2) // 2->3
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + positionOffset
                            * mScreen1_4);
                } else if (mCurrentPageIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_4 + ( positionOffset-1)
                            * mScreen1_4);
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

        mHomeLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        mEventLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });

        mSearchLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(2, true);
            }
        });


		mUserLinearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
				if (!AppManager.shareInstance().settingManager.getUserConfig().isSignedIn)
				{
					Activity login = new Activity();
					Intent intent = new Intent(mContext, LoginViewActivity.class);
					startActivityForResult(intent, DISPLAY_LOGIN);
				}else{
					mViewPager.setCurrentItem(3, true);
				}

            }
        });

        // TODO: tutorRequest();
    }


	public void setCurrentPage(int pageIndex)
	{
		mViewPager.setCurrentItem(pageIndex, true);
	}


	protected void resetImageView()
	{
        mHomeIcon.setImageResource(R.drawable.history_grey_96);
        mEventIcon.setImageResource(R.drawable.topic_grey_96);
        mSearchIcon.setImageResource(R.drawable.find_user2_grey_96);
        mUserIcon.setImageResource(R.drawable.user_male_circle_grey_96);

	}

	public void tutorRequest() {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, TutorRequestActivity.class);
		startActivityForResult(intent, 1);
	}

    public void getCurrentCity(){
        GPSTracker tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            double latitude = tracker.getLatitude();
            double longitude = tracker.getLongitude();

            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true_or_false";
            new RequestTask().execute(url);

        }


	}

    // Instantiate the RequestQueue.

}
