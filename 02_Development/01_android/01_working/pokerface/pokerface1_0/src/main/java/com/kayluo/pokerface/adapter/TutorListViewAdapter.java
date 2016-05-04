package com.kayluo.pokerface.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.dataModel.TutorEntity;


public class TutorListViewAdapter extends RecyclerView.Adapter<TutorViewHolder> {
    ArrayList<TutorEntity> tutor_list;
    LayoutInflater inflater;
    TutorViewHolder.OnItemClickListener listener;


    public TutorListViewAdapter(Context context, ArrayList<TutorEntity> tutor_list , TutorViewHolder.OnItemClickListener onItemClickListener) {

        this.tutor_list = tutor_list;
        this.inflater = LayoutInflater.from(context);
        this.listener = onItemClickListener;
    }
    public synchronized void updateList(ArrayList<TutorEntity> tutor_list)
    {
        this.tutor_list = tutor_list;
    }

    @Override
    public int getItemCount() {
        return tutor_list.size();
    }

    @Override
    public void onBindViewHolder(TutorViewHolder tutorViewHolder, int i) {
        TutorEntity entity = tutor_list.get(i);
        tutorViewHolder.name.setText(entity.getName());
        tutorViewHolder.slogan.setText(entity.getSignature());
        tutorViewHolder.career.setText(entity.getCareer());
        tutorViewHolder.eduLevel.setText(entity.getEdu_level());
        tutorViewHolder.university.setText(entity.getUniversity());
        new BitmapDownloaderTask(tutorViewHolder.personalPhoto)
                .execute(entity.getHead_photo());
        if (entity.getPrice() != null)
        {
            tutorViewHolder.price.setText("￥" + entity.getPrice() + " 起");
        }
        if (entity.getTotal_class_time() != null)
        {
            tutorViewHolder.totalClassTime.setText("累计" + entity.getTotal_class_time()+ "课时");
        }
        if (entity.getGender_eng() == "male") {
            tutorViewHolder.sex.setImageResource(R.drawable.male_blue_96);
        } else if (entity.getGender_eng() == "female") {
            tutorViewHolder.sex.setImageResource(R.drawable.female_red_96);
        }
        String ratingString = entity.getRating();
        if (ratingString != null && ratingString != "") {
            float rating = Float.parseFloat(ratingString);
            tutorViewHolder.ratingbar.setRating(rating);
        }

        tutorViewHolder.entity = entity;
    }

    @Override
    public TutorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_tutor_base, viewGroup, false);
        TutorEntity entity = tutor_list.get(i);
        TutorViewHolder viewHolder = new TutorViewHolder(itemView,listener,entity);
        return viewHolder;
    }






}




