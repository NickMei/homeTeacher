package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.BitmapDownloaderTask;
import com.kayluo.pokerface.dataModel.CommentRecord;

import java.util.ArrayList;

/**
 * Created by cxm170 on 2015/4/21.
 */
public class CommentRecordListAdapter extends RecyclerView.Adapter<CommentRecordViewHolder> {

    ArrayList<CommentRecord> commentRecordList;
    LayoutInflater inflater;
    CommentRecordViewHolder.OnItemClickListener listener;

    public CommentRecordListAdapter(Context context, ArrayList<CommentRecord> commentRecordList , CommentRecordViewHolder.OnItemClickListener onItemClickListener)
    {
        this.commentRecordList = commentRecordList;
        this.inflater = LayoutInflater.from(context);
        this.listener = onItemClickListener;
    }

    public synchronized void updateList(ArrayList<CommentRecord> commentRecordList)
    {
        this.commentRecordList = commentRecordList;
    }

    @Override
    public CommentRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view_commnet_record, parent, false);
        CommentRecord commentRecord = commentRecordList.get(viewType);
        CommentRecordViewHolder viewHolder = new CommentRecordViewHolder(itemView,listener,commentRecord);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentRecordViewHolder holder, int position) {
        CommentRecord entity = commentRecordList.get(position);
        if (entity.student_name != null) {
            holder.name.setText(entity.student_name);
        }
        if (entity.tutor_name != null) {
           holder.name.setText( entity.tutor_name);
        }
        holder.createDatetime.setText(entity.create_time.split(" ")[0]);
        holder.title.setText(entity.comment_type);
        holder.content.setText(entity.student_comment);
        holder.ratingBar.setNumStars(Integer.parseInt(entity.student_rating));
        if (entity.student_head_photo != null) {
            new BitmapDownloaderTask(holder.headPhoto).execute(entity.student_head_photo);
        }
        if (entity.tutor_head_photo != null) {
            new BitmapDownloaderTask(holder.headPhoto).execute(entity.tutor_head_photo);
        }
    }

    @Override
    public int getItemCount() {
        return commentRecordList.size();
    }

}
