package com.kayluo.pokerface.ui.main;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.database.UserProfile;
import com.kayluo.pokerface.ui.user.myBookmark.BookmarkActivity;
import com.kayluo.pokerface.ui.user.commentRecord.CommentRecordActivity;
import com.kayluo.pokerface.ui.user.LoginViewActivity;
import com.kayluo.pokerface.ui.user.SettingsActivity;
import com.kayluo.pokerface.ui.user.UserDetailActivity;
import com.kayluo.pokerface.ui.user.transactionRecord.UserTransactionRecordActivity;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.util.Utils;
import com.kayluo.pokerface.api.studentCenter.GetCommentListRequestResponse;
import com.kayluo.pokerface.api.studentCenter.GetStudentBasicInfoRequestResponse;
import com.kayluo.pokerface.api.studentCenter.GetStudentCourseRecordRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.ActivityRequestCode;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;


public class UserTabFragment extends Fragment
{
	private TextView username;
	private TextView myTotalClassTime;
	private TextView myComments;
	private TextView myTutors;
	private ImageView headPhoto;
	private View view;
	private RelativeLayout basicInfoView;

	private LinearLayout transactionRecordView;
	private LinearLayout settingsView;
	private LinearLayout commentView;
	private LinearLayout bookmarkView;
	private Boolean isMemberSignedIn;

	private LinearLayout orderWaitPayment;
	private LinearLayout orderExcuting;
	private LinearLayout orderFinished;

	private GetStudentCourseRecordRequestResponse getStudentCourseRecordRequestResponse;
	GetStudentBasicInfoRequestResponse getStudentBasicInfoRequestResponse;
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContext =  container.getContext();
        view =  inflater.inflate(R.layout.tab_user, container, false);
		initView();
		loadData();
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	public Boolean checkMemberInfo(){
		if (!isMemberSignedIn){
			Intent intent = new Intent(mContext, LoginViewActivity.class);
			getActivity().startActivityForResult(intent, ActivityRequestCode.DISPLAY_LOGIN);
		}
		return isMemberSignedIn;
	}


	private void initView()
	{
		username = (TextView) view.findViewById(R.id.tab_user_name);
		headPhoto = (ImageView) view.findViewById(R.id.tab_user_profile_pic);
		myComments = (TextView) view.findViewById(R.id.tab_user_my_comments);
		myTotalClassTime = (TextView) view.findViewById(R.id.tab_user_my_total_class_time);
		myTutors = (TextView) view.findViewById(R.id.tab_user_my_teachers);
		basicInfoView = (RelativeLayout) view.findViewById(R.id.user_basic_info_view);
		transactionRecordView = (LinearLayout) view.findViewById(R.id.tab_user_transaction_record_view);
		settingsView = (LinearLayout) view.findViewById(R.id.tab_user_settings);
		commentView =  (LinearLayout) view.findViewById(R.id.tab_user_comment_view);
		bookmarkView = (LinearLayout) view.findViewById(R.id.tab_user_bookmark_view);
		orderWaitPayment = (LinearLayout) view.findViewById(R.id.tab_user_order_wait_payment);
		orderExcuting = (LinearLayout) view.findViewById(R.id.tab_user_order_executing);
		orderFinished = (LinearLayout) view.findViewById(R.id.tab_user_order_finished);

		basicInfoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(checkMemberInfo()){
					Intent intent = new Intent(mContext, UserDetailActivity.class);
					startActivityForResult(intent, ActivityRequestCode.SHOW_USER_DETAIL);
				}
			}

		});

		transactionRecordView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo())
				{
					Intent intent = new Intent(mContext, UserTransactionRecordActivity.class);
					startActivity(intent);
				}
			}
		});


		settingsView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SettingsActivity.class);
				getActivity().startActivityForResult(intent, ActivityRequestCode.SETTINGS);
			}
		});


		commentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo()) {
					Intent intent = new Intent(mContext, CommentRecordActivity.class);
					intent.putExtra("targetType", GetCommentListRequestResponse.TargetType.STUDENT.name());
					startActivity(intent);
				}
			}
		});


		bookmarkView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo()) {
					Intent intent = new Intent(mContext, BookmarkActivity.class);
					startActivity(intent);
				}
			}
		});


		orderWaitPayment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo()) {
					Intent intent = new Intent(mContext, UserTransactionRecordActivity.class);
					intent.putExtra("index", 1);
					startActivity(intent);
				}
			}
		});


		orderExcuting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo()) {
					Intent intent = new Intent(mContext, UserTransactionRecordActivity.class);
					intent.putExtra("index", 2);
					startActivity(intent);
				}
			}
		});

		orderFinished.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkMemberInfo()) {
					Intent intent = new Intent(mContext, UserTransactionRecordActivity.class);
					intent.putExtra("index", 3);
					startActivity(intent);
				}
			}
		});

	}
	public void loadData()
	{
		isMemberSignedIn = AppManager.shareInstance().settingManager.getUserConfig().isSignedIn;
		if (!isMemberSignedIn) {
			username.setText("登录账号");
			myTutors.setVisibility(View.GONE);
			myTotalClassTime.setVisibility(View.GONE);
			myComments.setVisibility(View.GONE);
			headPhoto.setImageDrawable(Utils.getImageDrawable(R.drawable.ic_account_circle_white_48dp,mContext));
			return;
		}else{
			myTutors.setVisibility(View.VISIBLE);
			myTotalClassTime.setVisibility(View.VISIBLE);
			myComments.setVisibility(View.VISIBLE);
		}

		getStudentCourseRecordRequestResponse = new GetStudentCourseRecordRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo responseInfo) {
				myTutors.setText(getStudentCourseRecordRequestResponse.total_tutor);
				myTotalClassTime.setText(getStudentCourseRecordRequestResponse.total_class_time);
				myComments.setText(getStudentCourseRecordRequestResponse.total_comment);

			}

		});

		getStudentBasicInfoRequestResponse = new GetStudentBasicInfoRequestResponse(new RequestResponseBase.ResponseListener() {
			@Override
			public void onCompleted(ResponseInfo responseInfo) {
				if (responseInfo.returnCode == 0) {
					UserProfile profile = AppManager.shareInstance().settingManager.getUserConfig().profile;
//					userConfig.city = getStudentBasicInfoRequestResponse.basicInfo.city;
					profile.name = getStudentBasicInfoRequestResponse.basicInfo.name;
					profile.head_photo = getStudentBasicInfoRequestResponse.basicInfo.head_photo;
					username.setText(profile.name);
					new BitmapDownloaderTask(headPhoto).execute(profile.head_photo);
				}


			}

		});

	}


}
