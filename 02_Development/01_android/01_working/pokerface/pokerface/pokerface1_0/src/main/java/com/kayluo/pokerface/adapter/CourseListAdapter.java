package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class CourseListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private List<Course> list;

    public CourseListAdapter(Context context, List<Course> courseList) {

        list = courseList;
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
            convertView = inflater.inflate(R.layout.list_stage_item, null);
        }
        Course course = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.stageLisItemTextView);
        textView.setText(course.name);
        return convertView;
    }
}
