package com.kayluo.pokerface.UI.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;

public class HomeTabFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.tab_home, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MainActivity activity =  (MainActivity) getActivity();
		if (activity != null) {
			activity.showLocationButton();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}



}
