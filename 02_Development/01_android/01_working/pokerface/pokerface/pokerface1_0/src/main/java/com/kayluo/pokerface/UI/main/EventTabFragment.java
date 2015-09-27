package com.kayluo.pokerface.UI.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.TutorRequestActivity;

public class EventTabFragment extends Fragment
{
	Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContext = inflater.getContext();
		setHasOptionsMenu(true);
		((MainActivity)getActivity()).hideLocationButton();
		return inflater.inflate(R.layout.tab_event, container, false);
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_tab, menu);
		((MainActivity)getActivity()).hideLocationButton();
        super.onCreateOptionsMenu(menu, inflater);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == R.id.action_add) {
			Intent intent = new Intent(mContext,TutorRequestActivity.class);
			startActivityForResult(intent,1);
		}
		return super.onOptionsItemSelected(menuItem);
	}
}
