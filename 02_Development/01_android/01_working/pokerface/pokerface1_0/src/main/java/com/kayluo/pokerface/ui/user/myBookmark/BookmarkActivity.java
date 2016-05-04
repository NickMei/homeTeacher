package com.kayluo.pokerface.ui.user.myBookmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.TutorListViewAdapter;
import com.kayluo.pokerface.adapter.TutorViewHolder;
import com.kayluo.pokerface.api.studentCenter.GetStudentBookmarkListRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;
import com.kayluo.pokerface.ui.base.BaseActivity;

import java.util.ArrayList;

public class BookmarkActivity extends BaseActivity implements TutorViewHolder.OnItemClickListener {

    ArrayList<TutorEntity> tutor_list = new ArrayList<TutorEntity>();
    TutorListViewAdapter recListAdapter;
    RecyclerView recList;
    GetStudentBookmarkListRequestResponse getStudentBookmarkListRequestResponse;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        this.initToolBar();
        this.setUpRecView();
        this.getData();

    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.boomark_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }
    private void setUpRecView()
    {
        recList = (RecyclerView) findViewById(R.id.bookmark_List);
        recListAdapter = new TutorListViewAdapter(this,tutor_list,this);
        recList.setAdapter(recListAdapter);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
    }

    @Override
    public void onItemClick(TutorEntity entity) {

    }

    public void getData()
    {
        getStudentBookmarkListRequestResponse = new GetStudentBookmarkListRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == 0)
                {
                    tutor_list = getStudentBookmarkListRequestResponse.list;
                    recListAdapter.updateList(tutor_list);
                    recListAdapter.notifyDataSetChanged();
                    //TODO: handle no result found case
                }
            }
        });
    }
}
