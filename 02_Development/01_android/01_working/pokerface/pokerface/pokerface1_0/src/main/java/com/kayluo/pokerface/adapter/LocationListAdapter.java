package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.Province;

import java.util.List;

/**
 * Created by cxm170 on 2015/6/16.
 */

public class LocationListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    LayoutInflater inflater;
    private List<Province> locationList;

    public LocationListAdapter(Context context, List<Province> locationList) {

        this.locationList = locationList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_stage_item, null);
//            convertView = new TextView(inflater.getContext());
        }
        Province province = locationList.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.stageLisItemTextView);
        textView.setText(province.ProvinceName);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
