package com.kayluo.pokerface.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.Utils;
import com.kayluo.pokerface.adapter.SearchResultListAdapter;
import com.kayluo.pokerface.api.tutorInfo.GetTutorListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;
import com.kayluo.pokerface.ui.tutorList.TutorListViewActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends Activity {

    private TextView searchCancelButton;
    private EditText searchResultEditText;
    private LinearLayout searchHistoryView;
    private ListView searchResultListView;
    private LinearLayout searchResultView;
    private TextView clearSearchHistoryTextView;

    GetTutorListRequestResponse getTutorListRequestResponse;
    GetTutorListRequestResponse.Params params;
    private RequestResponseBase.ResponseListener responseListener;
    private SearchResultListAdapter searchResultListAdapter;

    private ArrayList<TutorEntity> tutor_list = new ArrayList<TutorEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initSubViews();
    }

    private void initSubViews()
    {
        searchCancelButton = (TextView) findViewById(R.id.search_cancel_button);
        searchCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchResultEditText.getText().length() > 0)
                {
                    searchResultView.setVisibility(View.GONE);
                    searchResultEditText.setText("");
                }
                else
                {
                    setResult(RESULT_CANCELED);
                    finish();
                }

            }
        });

        searchResultEditText = (EditText) findViewById(R.id.search_result_edit_text);
        if(searchResultEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        searchResultEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                params  = new GetTutorListRequestResponse().new Params();
                params.tutor_name = charSequence.toString();
                getTutorListRequestResponse =  new GetTutorListRequestResponse(params,responseListener);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchHistoryView = (LinearLayout) findViewById(R.id.search_history_view);
        searchResultView = (LinearLayout) findViewById(R.id.search_result_view);
        searchResultListView = (ListView) findViewById(R.id.search_result_list_view);

        createSearchResultItems();

        responseListener = new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo data) {
                if (params.tutor_name.length() == 0)
                {
                    searchHistoryView.removeAllViews();
                    createSearchResultItems();
                    searchResultView.setVisibility(View.GONE);

                    return;
                }
                ResponseInfo responseInfo = (ResponseInfo) data;
                if (responseInfo.returnCode == 0) {
                    GetTutorListRequestResponse mResponse = (GetTutorListRequestResponse) responseInfo.response;
                    tutor_list = mResponse.tutorList;
                    TutorEntity tutorEntity = new TutorEntity();
                    tutorEntity.setName("搜索 " + params.tutor_name);
                    tutor_list.add(0,tutorEntity);

                    searchResultListAdapter.update(tutor_list);
                    searchResultListAdapter.notifyDataSetChanged();
                    searchResultView.setVisibility(View.VISIBLE);
                }
            }
        };

        searchResultListAdapter =  new SearchResultListAdapter(SearchResultActivity.this,tutor_list);
        searchResultListView.setAdapter(searchResultListAdapter);
        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserConfig userConfig =  AppManager.shareInstance().settingManager.getUserConfig();
                String query = "";
                if (i == 0)
                {
                    query = params.tutor_name;
                }
                else
                {
                    TutorEntity tutorEntity = tutor_list.get(i);
                    query = tutorEntity.getName();
                }
                // check duplicate record
                if (!userConfig.searchHistory.contains(query))
                {
                    userConfig.searchHistory.add(query);
                    userConfig.saveToStorage(SearchResultActivity.this);
                }
                Intent intent = new Intent(SearchResultActivity.this, TutorListViewActivity.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
            }
        });

        clearSearchHistoryTextView = (TextView) findViewById(R.id.clear_search_history_text_view);
        clearSearchHistoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchHistoryView.removeAllViews();
                UserConfig userConfig =  AppManager.shareInstance().settingManager.getUserConfig();
                userConfig.searchHistory = new ArrayList<String>();
                userConfig.saveToStorage(SearchResultActivity.this);

            }
        });

    }

    private void createSearchResultItems()
    {
        List<String> searchHistory = AppManager.shareInstance().settingManager.getUserConfig().searchHistory;
        for (String history : searchHistory)
        {
            TextView textView = createTextView(history);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view;
                    Intent intent = new Intent(SearchResultActivity.this,TutorListViewActivity.class);
                    intent.putExtra("search_query",textView.getText());
                    startActivity(intent);
                }
            });
            searchHistoryView.addView(textView);
        }
    }

    private TextView createTextView(String text)
    {
        LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,Utils.getPixels(10,this),0);
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.text_view_blue_border);
        textView.setText(text);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(Utils.getPixels(10,this),Utils.getPixels(10,this),Utils.getPixels(10,this),Utils.getPixels(10,this));
        return textView;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold, R.anim.fade_out);
    }




}
