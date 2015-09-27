package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kayluo.pokerface.R;

import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/4/21.
 */
public class UserCommentsListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private ArrayList<String> list;

    public UserCommentsListAdapter(Context context, ArrayList tutor_list) {

//        this.tutor_list = tutor_list;
        list = new ArrayList<>();
        list.add("准时耐心，提升了我的学习兴趣");
        list.add("Mr. zhang");
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
            convertView = inflater.inflate(R.layout.element_comment_info,null);
        }
        String text = list.get(position);
        TextView textview = (TextView) convertView.findViewById(R.id.comment_content);
        textview.setText(text);
        return convertView;
    }
}
