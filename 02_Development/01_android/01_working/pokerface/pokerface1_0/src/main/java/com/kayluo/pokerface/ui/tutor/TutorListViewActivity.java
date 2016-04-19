package com.kayluo.pokerface.ui.tutor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.SearchResultListAdapter;
import com.kayluo.pokerface.ui.search.FilterViewActivity;
import com.kayluo.pokerface.adapter.TutorListViewAdapter;
import com.kayluo.pokerface.adapter.TutorViewHolder;
import com.kayluo.pokerface.api.tutorInfo.GetTutorListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.common.EActivityRequestCode;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.dataModel.TutorEntity;

import java.util.ArrayList;

public class TutorListViewActivity extends AppCompatActivity implements TutorViewHolder.OnItemClickListener{

    ArrayList<TutorEntity> tutorList = new ArrayList<TutorEntity>();
    ArrayList<TutorEntity> suggestionlist = new ArrayList<TutorEntity>();
    TutorListViewAdapter recListAdapter;
    RecyclerView recList;
    GetTutorListRequestResponse getTutorListRequestResponse;
    GetTutorListRequestResponse.Params params;

    int stageIndex;
    int courseIndex;
    int subCourseIndex;

    private RequestResponseBase.ResponseListener responseListener;
    private RequestResponseBase.ResponseListener searchResultResponseListener;

    private ImageButton navBackButton;
    private EditText searchResultEditText;
    private TextView searchCancelButton;
    private ListView searchResultListView;
    private LinearLayout searchResultView;
    private SearchResultListAdapter searchResultListAdapter;
    private TextWatcher searchResultEditTextWatcher;

    private TextView filterOrSortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list_view);
        initCustomToolBar();
        setUpView();
        setUpRecView();
    }

    private void showResults(String query)
    {
        hideSoftKeyBoard();
        params.tutor_name = query;
        getTutorListRequestResponse = new GetTutorListRequestResponse(params,responseListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==  EActivityRequestCode.TUTOR_LIST_FILTER.getValue())
        {
            if (resultCode == RESULT_OK)
            {
                String serializedString = data.getStringExtra("params");
                int stageIndex = data.getIntExtra("stageIndex", 0);
                int courseIndex = data.getIntExtra("courseIndex", 0);
                int subCourseIndex = data.getIntExtra("subCourseIndex", 0);
                params = new Gson().fromJson(serializedString,GetTutorListRequestResponse.Params.class);
                setTutorList(params);
                setCourseInfo(stageIndex, courseIndex, subCourseIndex);
            }
        }
        setUpFilterValue();
    }

    private void initCustomToolBar(){

        navBackButton = (ImageButton) findViewById(R.id.tutor_list_nav_back_button);
        searchResultView = (LinearLayout) findViewById(R.id.tutor_list_search_result_view);
        searchResultListView = (ListView) findViewById(R.id.tutor_list_search_result_list_view);
        searchResultEditText = (EditText) findViewById(R.id.tutor_list_search_result_edit_text);
        searchCancelButton = (TextView) findViewById(R.id.tutor_list_search_cancel_button);

        navBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        searchResultEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    navBackButton.setVisibility(View.GONE);
                    searchCancelButton.setVisibility(View.VISIBLE);
                }
            }
        });

        searchCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navBackButton.setVisibility(View.VISIBLE);
                searchCancelButton.setVisibility(View.GONE);
                searchResultEditText.clearFocus();
                hideSoftKeyBoard();
            }
        });

        // check intent data
        params  =  new GetTutorListRequestResponse().new Params();
        String query = getIntent().getStringExtra("search_query");
        if (query != null)
        {
            params.tutor_name = query;
            searchResultEditText.setText(query);
        }

        searchResultEditTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(getCurrentFocus() == searchResultEditText)
                {
                    params.tutor_name = charSequence.toString();
                    getTutorListRequestResponse.cancelPendingRequest();
                    getTutorListRequestResponse = new GetTutorListRequestResponse(params, searchResultResponseListener);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        searchResultEditText.addTextChangedListener(searchResultEditTextWatcher);

        searchResultResponseListener = new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo data) {
                if (params.tutor_name.length() == 0)
                {
                    setTutorList(params);
                    searchResultView.setVisibility(View.GONE);
                    return;
                }
                ResponseInfo responseInfo = (ResponseInfo) data;
                if (responseInfo.returnCode == 0) {
                    GetTutorListRequestResponse mResponse = (GetTutorListRequestResponse) responseInfo.response;
                    suggestionlist = mResponse.tutorList;
                    TutorEntity tutorEntity = new TutorEntity();
                    tutorEntity.setName("搜索 " + params.tutor_name);
                    suggestionlist.add(0, tutorEntity);
                    searchResultListAdapter.update(suggestionlist);
                    searchResultListAdapter.notifyDataSetChanged();
                    searchResultView.setVisibility(View.VISIBLE);
                }
            }
        };

        searchResultListAdapter =  new SearchResultListAdapter(TutorListViewActivity.this, tutorList);
        searchResultListView.setAdapter(searchResultListAdapter);
        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0)
                {
                    TutorEntity tutorEntity = suggestionlist.get(i);
                    params.tutor_name = tutorEntity.getName();
                }
                setTutorList(params);
                searchResultEditText.removeTextChangedListener(searchResultEditTextWatcher);
                searchResultEditText.setText(params.tutor_name);
                searchResultEditText.setSelection(params.tutor_name.length());
                searchResultEditText.addTextChangedListener(searchResultEditTextWatcher);
                searchResultView.setVisibility(View.GONE);
            }
        });
    }

    private void setUpView(){

        // handler for get TutorList API callback
        responseListener = new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(ResponseInfo data) {
                ResponseInfo responseInfo = (ResponseInfo) data;
                if (responseInfo.returnCode == 0)
                {
                    GetTutorListRequestResponse mResponse = (GetTutorListRequestResponse) responseInfo.response;
                    tutorList = mResponse.tutorList;
                    recListAdapter.updateList(tutorList);
                    recListAdapter.notifyDataSetChanged();
                    if (tutorList.size() == 0)
                    {
                        Toast.makeText(TutorListViewActivity.this,"无结果",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };

        getTutorListRequestResponse =  new GetTutorListRequestResponse(params,responseListener);

        filterOrSortButton = (TextView) findViewById(R.id.filter_and_sort_by_button);
        filterOrSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorListViewActivity.this, FilterViewActivity.class);
                String serializedString = new Gson().toJson(params);
                intent.putExtra("params", serializedString);
                intent.putExtra("stageIndex",stageIndex);
                intent.putExtra("courseIndex",courseIndex);
                intent.putExtra("subCourseIndex",subCourseIndex);
                startActivityForResult(intent, EActivityRequestCode.TUTOR_LIST_FILTER.getValue());
            }
        });
        searchResultEditText.clearFocus();

    }

    private void setUpRecView()
    {
        recList = (RecyclerView) findViewById(R.id.tutorList);
        recListAdapter = new TutorListViewAdapter(this, tutorList,this);
        recList.setAdapter(recListAdapter);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
    }

    private void setUpFilterValue()
    {
        String selectedCourse = getSelectedCourse();
        String selectedSortType = getSelectedSortType();
        String filterValue = "筛选: " + selectedCourse + " | 排序: " + selectedSortType;
        SpannableString ss1=  new SpannableString(filterValue);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss1.setSpan(boldSpan, filterValue.indexOf(selectedCourse), filterValue.lastIndexOf(selectedCourse), 0);
        ss1.setSpan(boldSpan, filterValue.indexOf(selectedSortType), filterValue.lastIndexOf(selectedSortType),0);
        filterOrSortButton.setText(ss1);

    }


    private String getSelectedCourse()
    {
        String text = "";
        if (params.stage.equals(""))
        {
            text = "全部";
        }
        else
        {
            if (params.course.equals(""))
            {
                text = params.stage;
            }
            else
            {
                if (params.sub_course.equals("全部") || params.sub_course.equals(""))
                {
                    text = params.stage + "/" + params.course;
                }
                else
                {
                    text = params.stage + "/" + params.course + "/" + params.sub_course;
                }
            }
        }
        return text;
    }

    private String getSelectedSortType()
    {
        String sortType = "";
        switch (params.order_by)
        {
            case "general":
                sortType = "综合排序";
                break;
            case "rating":
                sortType = "评价最高";
                break;
            case "total_class_time":
                sortType = "课时最多";
                break;
            case "price":
                sortType = "价格最低";
                break;
        }
        return sortType;
    }


    @Override
    public void onItemClick(TutorEntity entity) {
        Intent intent = new Intent(TutorListViewActivity.this, TutorDetailActivity.class);
        intent.putExtra("tutorId", entity.getTutor_id());
        startActivityForResult(intent, EActivityRequestCode.DISPLAY_TUTOR_DETAIL.getValue());
    }


    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setTutorList(GetTutorListRequestResponse.Params params)
    {
        this.params = params;
        getTutorListRequestResponse = new GetTutorListRequestResponse(params,responseListener);
    }

    public void setCourseInfo(int stageIndex, int courseIndex, int subCourseIndex)
    {
        this.stageIndex = stageIndex;
        this.courseIndex = courseIndex;
        this.subCourseIndex = subCourseIndex;
    }
}
