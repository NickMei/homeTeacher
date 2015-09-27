package com.kayluo.pokerface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.TutorEntity;


public class TutorViewHolder extends RecyclerView.ViewHolder {
    OnItemClickListener listener;
    TutorEntity entity;
    TextView name;
    TextView slogan;
    TextView eduLevel;
    TextView career;
    TextView university;
    TextView totalClassTime;
    TextView distance;
    TextView price;
    ImageView sex;
    ImageView personalPhoto;
    RatingBar ratingbar;

    public TutorViewHolder(View v,OnItemClickListener onItemClickListener,TutorEntity tutorEntity) {
        super(v);
        name = (TextView) v.findViewById(R.id.tutor_item_name);
        slogan = (TextView) v.findViewById(R.id.tutor_slogan);
        eduLevel = (TextView) v.findViewById(R.id.tutor_edu_level);
        career = (TextView) v.findViewById(R.id.tutor_career);
        university = (TextView) v.findViewById(R.id.tutor_university);
        totalClassTime = (TextView) v.findViewById(R.id.total_class_time);
        distance = (TextView) v.findViewById(R.id.tutor_distance);
        price = (TextView) v.findViewById(R.id.tutor_price);
        sex = (ImageView) v.findViewById(R.id.tutor_item_sex);
        personalPhoto = (ImageView) v.findViewById(R.id.tutor_item_photo);
        ratingbar = (RatingBar) v.findViewById(R.id.ratingBar);
        this.entity = tutorEntity;
        this.listener = onItemClickListener;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(entity);
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(TutorEntity entity);
    }

}