package com.kayluo.pokerface.UI.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.user.SettingsActivity;
import com.kayluo.pokerface.UI.user.UserDetailActivity;
import com.kayluo.pokerface.UI.user.UserTransactionRecordActivity;
import com.kayluo.pokerface.Util.BitmapDownloaderTask;
import com.kayluo.pokerface.api.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.api.GetStudentCourseRecordRequestResponse;
import com.kayluo.pokerface.api.LogoutRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;


public class UserTabFragment extends Fragment
{
	private TextView username;
	private TextView myTotalClassTime;
	private TextView myComments;
	private TextView myTutors;
	private ImageView headPhoto;
	private View view;
	private LinearLayout basicInfoView;
	private LinearLayout logoutView;
	private LinearLayout transactionRecordView;
	private LinearLayout settingsView;

	private GetStudentCourseRecordRequestResponse getStudentCourseRecordRequestResponse;
	private GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;
	Context thisContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		((MainActivity)getActivity()).hideLocationButton();
		thisContext =  container.getContext();
        view =  inflater.inflate(R.layout.tab_user, container, false);
		initView();
		loadData();
        return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MainActivity activity =  (MainActivity) getActivity();
		if (activity != null) {
			activity.hideLocationButton();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	static final int SHOW_USER_DETAIL = 2;  // The request code
	private void initView()
	{
		username = (TextView) view.findViewById(R.id.tab_user_name);
		headPhoto = (ImageView) view.findViewById(R.id.tab_user_profile_pic);
		myComments = (TextView) view.findViewById(R.id.tab_user_my_comments);
		myTotalClassTime = (TextView) view.findViewById(R.id.tab_user_my_total_class_time);
		myTutors = (TextView) view.findViewById(R.id.tab_user_my_teachers);
		basicInfoView = (LinearLayout) view.findViewById(R.id.linear_layout_user_basic_info);
		basicInfoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisContext, UserDetailActivity.class);
				startActivityForResult(intent, SHOW_USER_DETAIL);
			}
		});
		logoutView = (LinearLayout) view.findViewById(R.id.tab_user_logout);
		logoutView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppManager.shareInstance().settingManager.getUserConfig().logout(thisContext);
				MainActivity activity = (MainActivity) UserTabFragment.this.getActivity();
				activity.setCurrentPage(0);
				LogoutRequestResponse logoutRequest = new LogoutRequestResponse(new RequestResponseBase.ResponseListener() {
					@Override
					public void onCompleted(Object data) {
						Toast.makeText(thisContext, "登出成功", Toast.LENGTH_SHORT).show();
					}

				});
			}
		});

		transactionRecordView = (LinearLayout) view.findViewById(R.id.tab_user_transaction_record_view);
		transactionRecordView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisContext, UserTransactionRecordActivity.class);
				startActivity(intent);
			}
		});

		settingsView = (LinearLayout) view.findViewById(R.id.tab_user_settings);
		settingsView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisContext, SettingsActivity.class);
				startActivity(intent);
			}
		});

	}
	public void loadData()
	{
		getStudentCourseRecordRequestResponse = new GetStudentCourseRecordRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(Object data) {
				myTutors.setText(UserTabFragment.this.getStudentCourseRecordRequestResponse.total_tutor);
				myTotalClassTime.setText(UserTabFragment.this.getStudentCourseRecordRequestResponse.total_class_time);
				myComments.setText(UserTabFragment.this.getStudentCourseRecordRequestResponse.total_comment);

			}

		});

		getStudentBasicInfoRequestResponse = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(Object data) {
				username.setText(getStudentBasicInfoRequestResponse.basicInfo.name);
				new BitmapDownloaderTask(headPhoto)
						.execute(getStudentBasicInfoRequestResponse.basicInfo.head_photo);
			}

		});
	}


}
