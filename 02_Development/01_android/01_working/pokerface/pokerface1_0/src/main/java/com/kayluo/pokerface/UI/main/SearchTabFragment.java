package com.kayluo.pokerface.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;


public class SearchTabFragment extends Fragment{

    View v;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.tab_search, container, false);
        this.mContext = container.getContext();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tutor_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        mSearchView.setOnQueryTextListener(this);
//
//        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
//
//        // Assumes current activity is the searchable activity
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

    }



}
