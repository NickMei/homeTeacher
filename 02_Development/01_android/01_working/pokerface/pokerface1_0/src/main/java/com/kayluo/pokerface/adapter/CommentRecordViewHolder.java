package com.kayluo.pokerface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.CommentInfo;
import com.kayluo.pokerface.dataModel.CommentRecord;

/**
 * Created by cxm170 on 2015/9/21.
 */
public class CommentRecordViewHolder extends RecyclerView.ViewHolder{
    OnItemClickListener listener;
    CommentRecord commentRecord;
    TextView name;
    TextView title;
    TextView content;
    TextView createDatetime;
    ImageView headPhoto;
    RatingBar ratingBar;

    public CommentRecordViewHolder(View v, OnItemClickListener onItemClickListener, CommentRecord commentRecord) {
        super(v);
        name = (TextView) v.findViewById(R.id.comment_name);
        title = (TextView) v.findViewById(R.id.comment_title);
        content = (TextView) v.findViewById(R.id.comment_content);
        createDatetime = (TextView) v.findViewById(R.id.comment_datetime);
        headPhoto = (ImageView) v.findViewById(R.id.comment_head_photo);
        ratingBar = (RatingBar) v.findViewById(R.id.comment_ratingBar);
        this.commentRecord = commentRecord;
        this.listener = onItemClickListener;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(CommentRecordViewHolder.this.commentRecord);
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(CommentRecord commentRecord);
    }
}
