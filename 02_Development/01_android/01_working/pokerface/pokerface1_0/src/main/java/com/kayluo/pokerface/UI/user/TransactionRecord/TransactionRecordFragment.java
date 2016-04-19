package com.kayluo.pokerface.ui.user.transactionRecord;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.TransactionRecordListAdapter;
import com.kayluo.pokerface.adapter.TransactionRecordViewHolder;
import com.kayluo.pokerface.api.studentCenter.GetStudentOrderListRequestResponse;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TransactionRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionRecordFragment extends Fragment implements TransactionRecordViewHolder.OnItemClickListener {

    private RecyclerView recyclerView;
    private TransactionRecordListAdapter recListAdapter;
    private Context thisContext;
    private int mPage;
    private View view;
    private ArrayList<TransactionRecord> tranRecordList;
    private GetStudentOrderListRequestResponse getStudentOrderListRequestResponse;
    private static ArrayList<String> array = new ArrayList<String>(Arrays.asList("order_all", "order_wait_payment", "order_executing", "order_finished", "order_cancelled"));

    public static TransactionRecordFragment newInstance(int page) {
        TransactionRecordFragment fragmentDemo = new TransactionRecordFragment();
        Bundle args = new Bundle();
        args.putInt("index", page);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    private void createDataSource(int page)
    {
        String type = array.get(page);
        getStudentOrderListRequestResponse = new GetStudentOrderListRequestResponse(type, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo response) {
                if (response.returnCode == 0)
                {
                    recListAdapter.updateList(getStudentOrderListRequestResponse.transactionRecordList);
                    recListAdapter.notifyDataSetChanged();
                    if (getStudentOrderListRequestResponse.transactionRecordList.size() == 0)
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
        view = inflater.inflate(R.layout.fragment_transaction_record_page, container, false);
        int index = getArguments().getInt("index", 0);
        setUpRecView();
        createDataSource(index);
        return view;
    }

    private void setUpRecView()
    {
        tranRecordList = new ArrayList<TransactionRecord>();
        recyclerView = (RecyclerView) view.findViewById(R.id.tran_record_recycler_view);
        recListAdapter = new TransactionRecordListAdapter(thisContext,tranRecordList,this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recListAdapter);

    }

    @Override
    public void onItemClick(TransactionRecord transactionRecord) {

    }
}
