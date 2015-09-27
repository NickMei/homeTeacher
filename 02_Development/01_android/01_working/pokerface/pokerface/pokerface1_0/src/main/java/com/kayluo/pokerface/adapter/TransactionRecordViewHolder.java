package com.kayluo.pokerface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.TransactionRecord;
import com.kayluo.pokerface.dataModel.TutorEntity;

/**
 * Created by cxm170 on 2015/9/15.
 */
public class TransactionRecordViewHolder extends RecyclerView.ViewHolder {

    OnItemClickListener listener;
    TextView name;
    TextView status;
    TextView courseName;
    TextView teachingType;
    TextView totalTime;
    TextView totalPrice;
    TextView price;
    ImageView personalPhoto;
    TransactionRecord transactionRecord;

    public TransactionRecordViewHolder(View v,OnItemClickListener onItemClickListener,TransactionRecord tranRecord) {
        super(v);
        name = (TextView) v.findViewById(R.id.tran_record_name);
        status = (TextView) v.findViewById(R.id.tran_record_status);
        courseName = (TextView) v.findViewById(R.id.tran_record_course_name);
        teachingType = (TextView) v.findViewById(R.id.tran_record_teaching_type);
        totalPrice = (TextView) v.findViewById(R.id.tran_record_total_price);
        totalTime = (TextView) v.findViewById(R.id.tran_record_total_time);
        price = (TextView) v.findViewById(R.id.tran_record_price_per_hour);
        personalPhoto = (ImageView) v.findViewById(R.id.tran_record_user_pic);
        this.transactionRecord = tranRecord;
        this.listener = onItemClickListener;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(transactionRecord);
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(TransactionRecord transactionRecord);
    }
}
