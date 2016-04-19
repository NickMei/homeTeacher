package com.kayluo.pokerface.ui.user.commentRecord;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.CommentRecordListAdapter;
import com.kayluo.pokerface.adapter.CommentRecordViewHolder;
import com.kayluo.pokerface.api.studentCenter.GetCommentListRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.dataModel.CommentRecord;
import com.kayluo.pokerface.dataModel.ResponseInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommentRecordActivityFragment extends Fragment implements CommentRecordViewHolder.OnItemClickListener {

    public CommentRecordActivityFragment() {

    }

    private RecyclerView recyclerView;
    private CommentRecordListAdapter recListAdapter;
    private Context thisContext;
    private int mPage;
    private View view;
    private ArrayList<CommentRecord> commentRecordList;
    private GetCommentListRequestResponse getCommentListRequestResponse;
    private static ArrayList<String> array = new ArrayList<String>(Arrays.asList("comment_all", "comment_good", "comment_middle", "comment_bad"));
    private GetCommentListRequestResponse.TargetType targetType;
    private String tutorID;

    public static CommentRecordActivityFragment newInstance(int page, GetCommentListRequestResponse.TargetType targetType, String tutorID) {
        CommentRecordActivityFragment fragmentDemo = new CommentRecordActivityFragment();
        fragmentDemo.targetType = targetType;
        fragmentDemo.tutorID = tutorID;
        Bundle args = new Bundle();
        args.putInt("index", page);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    private void createDataSource(int page)
    {
        String type = array.get(page);
        getCommentListRequestResponse = new GetCommentListRequestResponse(targetType, type, tutorID, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == 0)
                {
                    recListAdapter.updateList(getCommentListRequestResponse.list);
                    recListAdapter.notifyDataSetChanged();
                    if (getCommentListRequestResponse.list.size() == 0)
                    {
                        Toast.makeText(thisContext, "无结果！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = container.getContext();
        view = inflater.inflate(R.layout.fragment_comment_record, container, false);
        int index = getArguments().getInt("index", 0);
        setUpRecView();
        createDataSource(index);
        return view;
    }

    private void setUpRecView()
    {
        commentRecordList = new ArrayList<CommentRecord>();
        recyclerView = (RecyclerView) view.findViewById(R.id.comment_record_recycler_view);
        recListAdapter = new CommentRecordListAdapter(thisContext,commentRecordList,this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recListAdapter);

    }


    @Override
    public void onItemClick(CommentRecord commentRecord) {

    }
}
