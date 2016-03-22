package com.kayluo.pokerface.adapter;

import android.content.Context;
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
    public int selectedStagePosition;
    private Context mContext;

    public StageListAdapter(Context context, List<Stage> stageList) {

        list = stageList;
        mContext = context;
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
        if (position == selectedStagePosition)
        {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else
        {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.lightWhite));
        }
        Stage stage = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.simple_list_view_item_text_view);
        textView.setText(stage.name);

        return convertView;
    }
}
