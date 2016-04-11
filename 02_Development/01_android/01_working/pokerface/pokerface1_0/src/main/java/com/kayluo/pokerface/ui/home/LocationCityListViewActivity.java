package com.kayluo.pokerface.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.LocationListAdapter;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.dataModel.City;

import java.util.ArrayList;
import java.util.List;

public class LocationCityListViewActivity extends AppCompatActivity {

    private ListView mListView;
    private Toolbar mToolbar;
    private TextView deviceLocationTextView;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_city_list_view);
        initToolBar();
        initSubViews();
    }

    private void initToolBar(){
        mToolbar = (Toolbar) findViewById(R.id.location_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    private void initSubViews()
    {
        cityList =  new ArrayList<City>();
        AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
        deviceLocationTextView = (TextView) findViewById(R.id.location_view_device_location_city);
        if (appConfig.locationCity == null)
        {
            deviceLocationTextView.setText("定位失败");
        }
        else
        {
            deviceLocationTextView.setText(appConfig.locationCity.cityName);
        }
        for (City city : appConfig.cityList)
        {
            if (appConfig.locationCity == null || city.cityID != appConfig.locationCity.cityID)
            {
                cityList.add(city);
            }
        }

        LocationListAdapter adapter = new LocationListAdapter(this,cityList);
        mListView = (ListView) findViewById(R.id.city_header_list_view);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = cityList.get(position);
                AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
                appConfig.locationCity = city;
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
        deviceLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

}
