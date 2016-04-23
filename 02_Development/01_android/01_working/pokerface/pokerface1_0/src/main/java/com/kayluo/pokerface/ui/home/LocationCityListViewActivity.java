package com.kayluo.pokerface.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.LocationListAdapter;
import com.kayluo.pokerface.api.base.RequestResponseBase;
import com.kayluo.pokerface.api.location.SetUserLocationInfoRequestResponse;
import com.kayluo.pokerface.core.AppConfig;
import com.kayluo.pokerface.core.AppManager;
import com.kayluo.pokerface.core.UserConfig;
import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.dataModel.ResponseInfo;
import com.kayluo.pokerface.ui.base.BaseActivity;
import com.kayluo.pokerface.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class LocationCityListViewActivity extends BaseActivity {

    private ListView mListView;
    private Toolbar mToolbar;
    private TextView deviceLocationTextView;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_city_list);
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
        final AppConfig appConfig = AppManager.shareInstance().settingManager.getAppConfig();
        deviceLocationTextView = (TextView) findViewById(R.id.location_view_device_location_city);
        deviceLocationTextView.setText(appConfig.locationCity.cityName);

        for (City city : appConfig.cityList)
        {
            if (!city.cityID.equals(appConfig.locationCity.cityID))
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
                UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
                userConfig.profile.city = city;
                if (Utils.isMemberSignedIn())
                {
                    userConfig.saveToStorage();
                    SetUserLocationInfoRequestResponse setUserLocationInfoRequestResponse = new SetUserLocationInfoRequestResponse(city.cityID, new RequestResponseBase.ResponseListener() {
                        @Override
                        public void onCompleted(ResponseInfo response) {

                        }
                    });
                }
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
                UserConfig userConfig = AppManager.shareInstance().settingManager.getUserConfig();
                userConfig.profile.city =  AppManager.shareInstance().settingManager.getAppConfig().locationCity;
                if(Utils.isMemberSignedIn())
                {
                    userConfig.saveToStorage();
                }
                finish();
            }
        });
    }

}
