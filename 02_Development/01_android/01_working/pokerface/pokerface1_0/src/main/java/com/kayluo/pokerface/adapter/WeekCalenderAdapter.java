package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cxm170 on 2015/3/25.
 */

public class WeekCalenderAdapter extends BaseAdapter {

    private Context mContext;

    // Constructor
    public WeekCalenderAdapter(Context c) {
        mContext = c;
        this.setUpList();
    }

    private List<DailySchedule> weekList= new ArrayList<DailySchedule>(8);
    private DailySchedule axis = new DailySchedule("","上午","下午","晚上");
    private DailySchedule monday = new DailySchedule("星期一","true","true","false");
    private DailySchedule tuesday = new DailySchedule("星期二","true","true","false");
    private DailySchedule wednesday = new DailySchedule("星期三","true","true","false");
    private DailySchedule thursday = new DailySchedule("星期四","true","true","false");
    private DailySchedule friday = new DailySchedule("星期五","true","true","false");
    private DailySchedule saturday = new DailySchedule("星期六","true","true","false");
    private DailySchedule sunday = new DailySchedule("星期日","true","true","false");

    private void setUpList(){
        weekList.add(axis);
        weekList.add(monday);
        weekList.add(tuesday);
        weekList.add(wednesday);
        weekList.add(thursday);
        weekList.add(friday);
        weekList.add(saturday);
        weekList.add(sunday);
    }

    public int getCount() {
        return weekList.size()*4;
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
        TextView imageView;
        int index = position%8;
        int timeIndex = position/8;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new TextView(mContext);
            imageView.setBackgroundColor(Color.WHITE);
            imageView.setText(getViewText(index,timeIndex));
        } else {
            imageView = (TextView) convertView;
        }


        return imageView;
    }

    public String getViewText(int index,int timeIndex)
    {
        String viewText = "";
        DailySchedule oneDay = weekList.get(index);
        switch (timeIndex){
            case 0:
                viewText = oneDay.name;
                break;
            case 1:
                viewText = oneDay.isMorningOK.toString();
                break;
            case 2:
                viewText = oneDay.isAfternoonOK.toString();
                break;
            case 3:
                viewText = oneDay.isNightOK.toString();
                break;
        }
        return viewText;

    }
}
