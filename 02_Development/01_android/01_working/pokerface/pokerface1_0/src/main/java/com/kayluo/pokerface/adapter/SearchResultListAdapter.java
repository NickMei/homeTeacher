package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.TutorEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2016-03-05.
 */
public class SearchResultListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<TutorEntity> searchResultList;

    public SearchResultListAdapter(Context mContext, ArrayList<TutorEntity> searchResultList) {
        this.searchResultList = searchResultList;
        this.inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return searchResultList.size();
    }

    @Override
    public Object getItem(int i) {
        return searchResultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_view_search_result, null);
        }
        TutorEntity tutorEntity = searchResultList.get(i);
        TextView textView = (TextView) view.findViewById(R.id.item_view_search_result_value);
        textView.setText(tutorEntity.getName());
        return view;
    }

    public void update(ArrayList<TutorEntity> tutor_list) {
        searchResultList = tutor_list;
    }
}
