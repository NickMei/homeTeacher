package com.kayluo.pokerface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.dataModel.CommentInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;

/**
 * Created by cxm170 on 2015/9/21.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder{
    OnItemClickListener listener;
    CommentInfo commentInfo;
    TextView name;
    TextView type;
    TextView content;
    TextView createDatetime;
    ImageView headPhoto;

    public CommentViewHolder(View v,OnItemClickListener onItemClickListener,CommentInfo commentInfo) {
        super(v);
        name = (TextView) v.findViewById(R.id.comment_user_name);
        type = (TextView) v.findViewById(R.id.comment_title);
        content = (TextView) v.findViewById(R.id.comment_content);
        createDatetime = (TextView) v.findViewById(R.id.comment_datetime);
        headPhoto = (ImageView) v.findViewById(R.id.comment_user_pic);
        this.commentInfo = commentInfo;
        this.listener = onItemClickListener;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(CommentViewHolder.this.commentInfo);
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(CommentInfo commentInfo);
    }
}
