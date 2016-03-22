package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kayluo.pokerface.R;

import java.util.List;

/**
 * Created by cxm170 on 2015/6/16.
 */

public class LocationListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private List<String> locationList;

    public LocationListAdapter(Context mContext, List<String> cityList) {
        this.locationList = cityList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_view_simple_list, null);
        }
        String city = locationList.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.simple_list_view_item_text_view);
        textView.setText(city);
        return convertView;
    }

}
