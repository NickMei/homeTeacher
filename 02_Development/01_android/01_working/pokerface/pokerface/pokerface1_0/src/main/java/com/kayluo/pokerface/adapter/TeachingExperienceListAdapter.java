package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/4/21.
 */
public class TeachingExperienceListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<String> tutor_list ;

    public TeachingExperienceListAdapter(Context context, ArrayList tutor_list) {

        this.tutor_list = new ArrayList<String>();
        this.tutor_list.add("2012-01  至  2013-06 大学勤奋\n" +
                "大学期间在校勤奋，目前已结束的三个学期成绩均在本专业名列前茅。学习大一上学期在“活力社区”朱房中心为进城务工子弟辅导功课，主要辅导语文英语，在此过程中与小同学建立了良好的友谊，一定程度上也促进了他们的学习成绩的提升，获得学生的好评及机构的赞赏。\n");
        this.tutor_list.add("2010-01  至  2010-06 高中获奖\n" +
                "2013年河南省高考，高中时成绩优异，曾多次取得年级前十名及高中所在县区前十名。曾获得校级作文大赛一等奖（第一名），英语写作大赛二等奖\n");
        this.tutor_list.add("2008-09  至  2009-12 初中时成绩优秀\n" +
                "初中时成绩优秀，尤以语文英语为佳，初中时在语文方面较擅长作文写作和阅读题作答；关于英语，在完形填空的答题和知识点的记忆方面有一定心得。多次荣获三好学生。曾多次参加演讲比赛、辩论赛、作文大赛、朗诵比赛，获奖项若干。有文章在校报发表。总成绩也多次位居年级前十名，初中结束时以优异成绩考取当地重点高中。\n");
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return tutor_list.size();
    }

    @Override
    public Object getItem(int position) {
        return tutor_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new TextView(inflater.getContext());
        }

        String text = "";
        for (String item : this.tutor_list)
        {
            text +=item + "\n";



        }
        ((TextView)convertView).setText(text);

        return convertView;
    }
}