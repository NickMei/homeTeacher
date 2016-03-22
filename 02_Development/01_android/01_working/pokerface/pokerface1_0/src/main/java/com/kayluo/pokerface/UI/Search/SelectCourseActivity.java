package com.kayluo.pokerface.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.CourseListAdapter;
import com.kayluo.pokerface.adapter.StageListAdapter;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.Stage;

import java.util.List;

public class SelectCourseActivity extends AppCompatActivity {

    private ListView listView;
    private ListView subListView;
    private Toolbar mToolbar;
    private CourseListAdapter courseListAdapter;
    private StageListAdapter stageListAdapter;
    private List<Stage> stageList;

    int stageIndex;
    int courseIndex;
    int subCourseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        initToolBar();
        loadCourseInfo();
        setUpViews();
    }

    private void loadCourseInfo() {
        stageIndex = getIntent().getIntExtra("stageIndex", 0);
        courseIndex = getIntent().getIntExtra("courseIndex", 0);
        subCourseIndex = getIntent().getIntExtra("subCourseIndex", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_select_course_button:
                saveCourseInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCourseInfo()
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("stageIndex",stageListAdapter.selectedStagePosition);
        returnIntent.putExtra("courseIndex",courseListAdapter.selectedCoursePosition);
        returnIntent.putExtra("subCourseIndex",courseListAdapter.getSelectedSubCoursePosition());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.select_course_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourseInfo();
            }
        });

    }

    private void setUpViews()
    {

        AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
        stageList = appConfig.stageList;
        // setup ListView
        listView = (ListView) findViewById(R.id.select_course_list_View);
        stageListAdapter = new StageListAdapter(this,appConfig.stageList);
        stageListAdapter.selectedStagePosition = stageIndex;
        listView.setAdapter(stageListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stageListAdapter.selectedStagePosition = i;
                stageListAdapter.notifyDataSetChanged();
                List<Course> list = stageList.get(i).courseList;
                courseListAdapter.setList(list);
                courseListAdapter.selectedCoursePosition = 0;
                courseListAdapter.selectedSubCoursePosition = 0;
                courseListAdapter.notifyDataSetChanged();
            }
        });

        // setup SubListView
        subListView = (ListView) findViewById(R.id.select_course_sub_list_View);
        List<Course> list = stageList.get(stageIndex).courseList;
        courseListAdapter = new CourseListAdapter(SelectCourseActivity.this, list);
        courseListAdapter.selectedCoursePosition = courseIndex;
        courseListAdapter.selectedSubCoursePosition = subCourseIndex;
        subListView.setAdapter(courseListAdapter);
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                courseListAdapter.selectedCoursePosition = i;
                courseListAdapter.selectedSubCoursePosition = 0;
                courseListAdapter.notifyDataSetChanged();
            }
        });
    }

}
