package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.dataModel.TransactionRecord;

import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/9/15.
 */
public class TransactionRecordListAdapter extends RecyclerView.Adapter<TransactionRecordViewHolder>{

    ArrayList<TransactionRecord> tranRecordList;
    LayoutInflater inflater;
    TransactionRecordViewHolder.OnItemClickListener listener;


    public TransactionRecordListAdapter(Context context, ArrayList<TransactionRecord> tranRecordList , TransactionRecordViewHolder.OnItemClickListener onItemClickListener) {

        this.tranRecordList = tranRecordList;
        this.inflater = LayoutInflater.from(context);
        this.listener = onItemClickListener;
    }
    public synchronized void updateList(ArrayList<TransactionRecord> tranRecordList)
    {
        this.tranRecordList = tranRecordList;
    }

    @Override
    public int getItemCount() {
        return tranRecordList.size();
    }

    @Override
    public void onBindViewHolder(TransactionRecordViewHolder viewHolder, int i) {
        TransactionRecord entity = tranRecordList.get(i);
        viewHolder.name.setText(entity.name);
        viewHolder.status.setText(entity.status);
        viewHolder.courseName.setText(entity.course);
        viewHolder.price.setText("￥ " + entity.price +"/小时");
        viewHolder.totalPrice.setText("总价：￥" + entity.total_price);
        viewHolder.totalTime.setText(entity.total_time);
        if (entity.head_photo != null)
        new BitmapDownloaderTask(viewHolder.personalPhoto)
                .execute(entity.head_photo);
        viewHolder.teachingType.setText(entity.teach_way);
        viewHolder.transactionRecord = entity;
    }

    @Override
    public TransactionRecordViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_transaction_record, viewGroup, false);
        TransactionRecord transactionRecord = tranRecordList.get(i);
        TransactionRecordViewHolder viewHolder = new TransactionRecordViewHolder(itemView,listener,transactionRecord);
        return viewHolder;
    }
}
