package com.kayluo.pokerface.UI.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.TutorDetailActivity;
import com.kayluo.pokerface.adapter.CourseListAdapter;
import com.kayluo.pokerface.adapter.StageListAdapter;
import com.kayluo.pokerface.adapter.SubCourseAdapter;
import com.kayluo.pokerface.adapter.TutorListViewAdapter;
import com.kayluo.pokerface.adapter.TutorViewHolder;
import com.kayluo.pokerface.api.GetCourseListRequestResponse;
import com.kayluo.pokerface.api.GetStageListRequestResponse;
import com.kayluo.pokerface.api.GetSubCourseListRequestResponse;
import com.kayluo.pokerface.api.GetTutorListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.Stage;
import com.kayluo.pokerface.dataModel.SubCourse;
import com.kayluo.pokerface.dataModel.TutorEntity;

import org.w3c.dom.Text;


public class SearchTabFragment extends Fragment implements TutorViewHolder.OnItemClickListener
{

    ArrayList<TutorEntity> tutor_list = new ArrayList<TutorEntity>();
    TutorListViewAdapter recListAdapter;
    RecyclerView recList;
    ListView stageListView;
    ListView courseListView;
    ListView subCourseListView;
    GetStageListRequestResponse getStageListRequestResponse;
    GetCourseListRequestResponse getCourseListRequestResponse;
    GetSubCourseListRequestResponse getSubCourseListRequestResponse;
    GetTutorListRequestResponse getTutorListRequestResponse;

    View v;
    View mainView;
    Context thiscontext;
    LinearLayout categoryView;
    LinearLayout filterView;
    LinearLayout orderView;
    ActionBar mToolbar;

    private Spinner careerInfoSelector;
    private Spinner genderInfoSelector;
    private Spinner dayInfoSelector;
    private Spinner timeInfoSelector;
    private EditText priceMaxEditText;
    private EditText priceMinEditText;
    private RequestResponseBase.ResponseListener responseListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.tab_search, container, false);
        mainView = inflater.inflate(R.layout.activity_main, container, false);
        this.thiscontext = container.getContext();
        setUpView();
        setUpRecView();
        getData();
        ((MainActivity)getActivity()).hideLocationButton();
    	return v;
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_tab, menu);
        ((MainActivity)getActivity()).hideLocationButton();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            Toast.makeText(thiscontext, "Share", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setUpView(){
        stageListView = (ListView) v.findViewById(R.id.mainCategory);
        courseListView  = (ListView) v.findViewById(R.id.subCategory);
        subCourseListView = (ListView) v.findViewById(R.id.secondSubcategory);
        categoryView = (LinearLayout) v.findViewById(R.id.categoryView);
        filterView = (LinearLayout) v.findViewById(R.id.filterView);
        orderView = (LinearLayout) v.findViewById(R.id.orderView);

        Button categoryButton = (Button) v.findViewById(R.id.categoryButton);
        categoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onCategoryButtonClicked();

            }
        });

        Button filterButton = (Button) v.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onFilterButtonClicked();

            }
        });

        Button orderButton = (Button) v.findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onOrderButtonClicked();

            }
        });


        LinearLayout categoryViewBackground = (LinearLayout) categoryView.findViewById(R.id.categoryViewBackground);
        categoryViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryButtonClicked();
            }
        });

        setUpFilterView();
        setUpOrderView();



        // handler for get TutorList API callback
        responseListener = new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                tutor_list = (ArrayList<TutorEntity>) data;
                recListAdapter.updateList(tutor_list);
                recListAdapter.notifyDataSetChanged();
                if (((ArrayList<TutorEntity>) data).size() == 0)
                {
                    Toast.makeText(thiscontext, "无结果！", Toast.LENGTH_SHORT).show();
                }

            }
        };

    }

    private void setUpFilterView(){


        LinearLayout filterViewBackground = (LinearLayout) filterView.findViewById(R.id.filterViewBackground);
        filterViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterButtonClicked();
            }
        });
        careerInfoSelector = (Spinner) filterView.findViewById(R.id.career_info_selector);
        genderInfoSelector = (Spinner) filterView.findViewById(R.id.gender_info_selector);
        dayInfoSelector = (Spinner) filterView.findViewById(R.id.day_selector);
        timeInfoSelector = (Spinner) filterView.findViewById(R.id.time_selector);
        priceMaxEditText = (EditText) filterView.findViewById(R.id.filter_price_max);
        priceMinEditText = (EditText) filterView.findViewById(R.id.filter_price_min);

        filterView.findViewById(R.id.filter_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = "";
                String career = "";
                String priceMax = "";
                String priceMin = "";
                String day = "";
                String time = "";

                switch (careerInfoSelector.getSelectedItemPosition()){
                    case 0:
                        break;
                    case 1:
                    case 2:
                    case 3:
                        career = (String) careerInfoSelector.getSelectedItem();
                    default:
                        break;
                }

                switch (genderInfoSelector.getSelectedItemPosition()){
                    case 0:
                        break;
                    case 1:
                        gender = "male";
                        break;
                    case 2:
                        gender = "female";
                        break;
                }
                priceMax = priceMaxEditText.getText().toString();
                priceMin = priceMinEditText.getText().toString();

                switch (dayInfoSelector.getSelectedItemPosition())
                {
                    case 0:
                        break;
                    case 1:
                        day = "monday";
                        break;
                    case 2:
                        day = "tuesday";
                        break;
                    case 3:
                        day = "wednesday";
                        break;
                    case 4:
                        day = "thursday";
                        break;
                    case 5:
                        day = "friday";
                        break;
                    case 6:
                        day = "saturday";
                        break;
                    case 7:
                        day = "sunday";
                        break;
                }

                switch (timeInfoSelector.getSelectedItemPosition()){
                    case 0:
                        break;
                    case 1:
                        time = "morning";
                        break;
                    case 2:
                        time = "afternoon";
                        break;
                    case 3:
                        time = "evening";
                        break;
                }
                getTutorListData(gender,career,"",priceMin,priceMax,day,time);
                onFilterButtonClicked();
            }
        });

    }

    private void setUpOrderView(){

        LinearLayout orderViewBackground = (LinearLayout) orderView.findViewById(R.id.orderViewBackground);
        orderViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderButtonClicked();
            }
        });

        orderView.findViewById(R.id.order_by_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutorListData("general", "");
                onOrderButtonClicked();
            }
        });

        orderView.findViewById(R.id.order_by_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutorListData("rating","DESC");
                onOrderButtonClicked();
            }
        });

        orderView.findViewById(R.id.order_by_total_class_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutorListData("total_class_time","DESC");
                onOrderButtonClicked();
            }
        });

        orderView.findViewById(R.id.order_by_price_asc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutorListData("price","ASC");
                onOrderButtonClicked();
            }
        });

        orderView.findViewById(R.id.order_by_price_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutorListData("price", "DESC");
                onOrderButtonClicked();
            }
        });
    }
    public void onCategoryButtonClicked()
    {

        int visibility = categoryView.getVisibility();

        if(visibility==View.GONE) {
            showStageList();
            categoryView.setVisibility(View.VISIBLE);
            orderView.setVisibility(View.GONE);
            filterView.setVisibility(View.GONE);
        }else
            categoryView.setVisibility(View.GONE);
    }

    public void onFilterButtonClicked()
    {
        int visibility = filterView.getVisibility();

        if(visibility==View.GONE) {

            filterView.setVisibility(View.VISIBLE);
            orderView.setVisibility(View.GONE);
            categoryView.setVisibility(View.GONE);
        }else
            filterView.setVisibility(View.GONE);
    }

    public void onOrderButtonClicked()
    {
        int visibility = orderView.getVisibility();

        if(visibility==View.GONE) {
            orderView.setVisibility(View.VISIBLE);
            categoryView.setVisibility(View.GONE);
            filterView.setVisibility(View.GONE);
        }else
            orderView.setVisibility(View.GONE);
    }

    private void setUpRecView()
    {
        recList = (RecyclerView) v.findViewById(R.id.tutorList);
        recListAdapter = new TutorListViewAdapter(thiscontext,tutor_list,this);
        recList.setAdapter(recListAdapter);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(thiscontext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
    }

	private void getData() {
        getTutorListData("", "", "");
        getStageListRequestResponse = new GetStageListRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {

            }
        });
    }
	

    public void showStageList(){
        // add your items, this can be done programatically
        // your items can be from a database
        // Tag used to cancel the request

        StageListAdapter adapter = new StageListAdapter(this,thiscontext,getStageListRequestResponse.stageList);

        // create a new ListView, set the adapter and item click listener
        stageListView.setAdapter(adapter);
        stageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCourseListItemClicked(position);

            }
        });
    }

    static final int DISPLAY_TUTOR_DETAIL = 1;  // The request code
    @Override
    public void onItemClick(TutorEntity entity) {
        Intent intent = new Intent(thiscontext, TutorDetailActivity.class);
        intent.putExtra("tutorId", entity.getTutor_id());
        startActivityForResult(intent, DISPLAY_TUTOR_DETAIL);
    }


    public void onCourseListItemClicked(int position)
    {
        final Stage stage = getStageListRequestResponse.stageList.get(position);
        getCourseListRequestResponse = new GetCourseListRequestResponse(stage.stageId, new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                CourseListAdapter courseListAdapter = new CourseListAdapter(thiscontext,(List<Course>)data);
                if (((List<Course>) data).size() == 0)
                {
                    getTutorListData(stage.name,"","");
                    onCategoryButtonClicked();
                }else {
                    courseListView.setAdapter(courseListAdapter);
                    courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final Course course = getCourseListRequestResponse.courseList.get(position);
                            getSubCourseListRequestResponse = new GetSubCourseListRequestResponse(course.id, new RequestResponseBase.ResponseListener() {
                                @Override
                                public void onCompleted(Object data) {
                                    SubCourseAdapter subCourseAdapter = new SubCourseAdapter(thiscontext, (List<SubCourse>) data);
                                    if (((List<SubCourse>) data).size() == 0) {
                                        getTutorListData(stage.name, course.name, "");
                                        onCategoryButtonClicked();
                                    } else {
                                        subCourseListView.setAdapter(subCourseAdapter);
                                        subCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                final SubCourse subCourse = getSubCourseListRequestResponse.subCourseList.get(position);
                                                getTutorListData(stage.name, course.name, subCourse.name);
                                            }
                                        });
                                    }
                                }

                            });


                        }
                    });
                }
            }

        });
    }

    private void getTutorListData(String stage, String course, String subCourse )
    {
        getTutorListRequestResponse = new GetTutorListRequestResponse(responseListener);
        getTutorListRequestResponse.stage = stage;
        getTutorListRequestResponse.course = course;
        getTutorListRequestResponse.subCourse = subCourse;
        getTutorListRequestResponse.sendRequest();
    }

    private void getTutorListData(String genderEng, String career, String district, String priceMin, String priceMax, String dayEng,String periodEng )
    {
        getTutorListRequestResponse = new GetTutorListRequestResponse(responseListener);
        getTutorListRequestResponse.gender_eng  = genderEng;
        getTutorListRequestResponse.career = career;
        getTutorListRequestResponse.district = district;
        getTutorListRequestResponse.price_min = priceMin;
        getTutorListRequestResponse.price_max = priceMax;
        getTutorListRequestResponse.day_eng = dayEng;
        getTutorListRequestResponse.period_eng = periodEng;
        getTutorListRequestResponse.sendRequest();
    }

    private void getTutorListData(String orderBy, String orderDirec)
    {
        getTutorListRequestResponse = new GetTutorListRequestResponse(responseListener);
        getTutorListRequestResponse.order_by = orderBy;
        getTutorListRequestResponse.order_direc = orderDirec;
        getTutorListRequestResponse.sendRequest();
    }

}
