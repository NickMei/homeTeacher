package com.kayluo.pokerface.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.api.tutorInfo.GetTutorListRequestResponse;
import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.ui.home.LocationCityListViewActivity;
import com.kayluo.pokerface.ui.search.SearchResultActivity;
import com.kayluo.pokerface.ui.tutor.TutorListViewActivity;
import com.kayluo.pokerface.util.geoLocation.LocationService;
import com.kayluo.pokerface.common.EActivityRequestCode;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeTabFragment extends Fragment
{
	private Context mContext;
	private View v;
	private Button selectLocationBtn;
	private LocationService locationService;
	private ImageButton searchButton;
	private ConvenientBanner adBanner;

	private TextView primarySchoolEducationTextView,
			middleSchoolEducationTextView,
			highSchoolEducationTextView,
			universityEducationTextView,
			enteringMiddleSchoolExamTextView,
			enteringHighSchoolExamTextView,
			enteringUniversityExamTextView,
			studyAbroadEducationTextView,
			foreignLanguageEducationTextView,
	        artEducationTextView,
			psychologicalEducationTextView,
			physicalEducationTextView;

	private float lastX;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContext = container.getContext();
		v =  inflater.inflate(R.layout.tab_home, container, false);
		initViews();
		return v;
	}

	public void setLocation(City location)
	{
		selectLocationBtn.setText("[ " + location.cityName + " ]");
	}

	public void initViews() {

		primarySchoolEducationTextView = (TextView) v.findViewById(R.id.tab_home_primary_school_education_text_view);
		middleSchoolEducationTextView = (TextView) v.findViewById(R.id.tab_home_middle_school_education_text_view);
		highSchoolEducationTextView = (TextView) v.findViewById(R.id.tab_home_high_school_education_text_view);
		universityEducationTextView = (TextView) v.findViewById(R.id.tab_home_university_education_text_view);
		enteringMiddleSchoolExamTextView = (TextView) v.findViewById(R.id.tab_home_entering_middle_school_exam_text_view);
		enteringHighSchoolExamTextView = (TextView) v.findViewById(R.id.tab_home_entering_high_school_exam_text_view);
		enteringUniversityExamTextView = (TextView) v.findViewById(R.id.tab_home_entering_university_exam_text_view);
		studyAbroadEducationTextView = (TextView) v.findViewById(R.id.tab_home_study_abroad_education_text_view);
		foreignLanguageEducationTextView = (TextView) v.findViewById(R.id.tab_home_foreign_language_education_text_view);
		artEducationTextView = (TextView) v.findViewById(R.id.tab_home_art_education_text_view);
		psychologicalEducationTextView = (TextView) v.findViewById(R.id.tab_home_psychological_education_text_view);
		physicalEducationTextView = (TextView) v.findViewById(R.id.tab_home_physical_education_text_view);


		View.OnClickListener onGenreButtonClicked = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView textView = (TextView)view;
				String stageName = textView.getText().toString();
				int stageIndex = AppManager.shareInstance().settingManager.getAppConfig().getStageIndexByStageName(stageName);
				Intent intent = new Intent(mContext, TutorListViewActivity.class);
				intent.putExtra("stageIndex",stageIndex);
				getActivity().startActivityForResult(intent,EActivityRequestCode.TUTOR_LIST.getValue());
			}
		};

		primarySchoolEducationTextView.setOnClickListener(onGenreButtonClicked);
		middleSchoolEducationTextView.setOnClickListener(onGenreButtonClicked);
		highSchoolEducationTextView.setOnClickListener(onGenreButtonClicked);
		universityEducationTextView.setOnClickListener(onGenreButtonClicked);
		enteringMiddleSchoolExamTextView.setOnClickListener(onGenreButtonClicked);
		enteringHighSchoolExamTextView.setOnClickListener(onGenreButtonClicked);
		enteringUniversityExamTextView.setOnClickListener(onGenreButtonClicked);
		studyAbroadEducationTextView.setOnClickListener(onGenreButtonClicked);
		artEducationTextView.setOnClickListener(onGenreButtonClicked);
		foreignLanguageEducationTextView.setOnClickListener(onGenreButtonClicked);
		psychologicalEducationTextView.setOnClickListener(onGenreButtonClicked);
		physicalEducationTextView.setOnClickListener(onGenreButtonClicked);


		searchButton = (ImageButton) v.findViewById(R.id.search_button);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, SearchResultActivity.class);
				getActivity().startActivityForResult(intent, EActivityRequestCode.SEARCH_RESULTS.getValue());
				getActivity().overridePendingTransition(R.anim.hold, R.anim.fade_in);
			}
		});
		// --- init views ------
		selectLocationBtn = (Button) v.findViewById(R.id.home_page_select_location_button);
		selectLocationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, LocationCityListViewActivity.class);
				getActivity().startActivityForResult(intent, EActivityRequestCode.SELECT_LOCATION.getValue());
			}
		});

		detectLocation();
		createAdBanner();
	}

	private void createAdBanner()
	{

		ArrayList<Integer> localImages = new ArrayList<Integer>();
		localImages.add(R.drawable.banner1);
		localImages.add(R.drawable.banner2);
		localImages.add(R.drawable.banner3);
		adBanner = (ConvenientBanner) v.findViewById(R.id.tab_home_ad_banner);
		adBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
			@Override
			public LocalImageHolderView createHolder() {
				return new LocalImageHolderView();
			}
		}, localImages)
				.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
		.startTurning(2000);
		//设置翻页的效果，不需要翻页效果可用不设
		//.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响

	}

	public class LocalImageHolderView implements Holder<Integer> {
		private ImageView imageView;
		@Override
		public View createView(Context context) {
			imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.FIT_START);
			return imageView;
		}

		@Override
		public void UpdateUI(Context context, final int position, Integer data) {
			imageView.setImageResource(data);
		}
	}

	private void detectLocation()
	{
		// -----------location config ------------
		locationService = AppManager.shareInstance().locationService;
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
		int type = getActivity().getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		//开始定位
		locationService.start();
	}

	private BDLocationListener mListener =  new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			StringBuffer sb = new StringBuffer(256);
			if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
				AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
				appConfig.locationCity.cityName = bdLocation.getCity();
				for (City city : appConfig.cityList)
				{
					if(city.cityName.contains(appConfig.locationCity.cityName))
					{
						appConfig.locationCity.cityID = city.cityID;
					}
				}

				UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
				if(userConfig.isSignedIn && !userConfig.profile.city.cityID.equals("0"))
				{
					selectLocationBtn.setText("[ " + userConfig.profile.city.cityName + " ]");
				}
				else
				{
					selectLocationBtn.setText("[ " + appConfig.locationCity.cityName + " ]");
					locationService.unregisterListener(mListener); //注销掉监听
					locationService.stop(); //停止定位服务
				}
			}else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			sb.append("\nlocationdescribe : ");
			sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
			List<Poi> list = bdLocation.getPoiList();// POI数据
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	};


}
