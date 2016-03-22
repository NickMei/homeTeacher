package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.SubCourse;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/16.
 */
public class SubCourseAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<SubCourse> list;

    public SubCourseAdapter(Context context, List<SubCourse> subCourseList) {

        list = subCourseList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        SubCourse subCourse = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.simple_list_view_item_text_view);
        textView.setText(subCourse.name);
        return convertView;
    }
}
