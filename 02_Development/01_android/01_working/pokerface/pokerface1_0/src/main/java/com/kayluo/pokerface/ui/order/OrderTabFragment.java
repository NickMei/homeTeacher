package com.kayluo.pokerface.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;

public class OrderTabFragment extends Fragment
{
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContext = inflater.getContext();
		return inflater.inflate(R.layout.tab_order, container, false);
	}

}
