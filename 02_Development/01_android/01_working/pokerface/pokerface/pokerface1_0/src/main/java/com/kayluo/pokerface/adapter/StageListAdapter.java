package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.Stage;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/11.
 */
public class StageListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private List<Stage> list;
    Fragment fragment;

    public StageListAdapter(Fragment fragment ,Context context, List<Stage> stageList) {

        this.fragment = fragment;
        list = stageList;
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
//            convertView = new TextView(inflater.getContext());
        }
        Stage stage = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.stageLisItemTextView);
        textView.setText(stage.name);

        return convertView;
    }
}
