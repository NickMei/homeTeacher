package com.kayluo.pokerface.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.kayluo.pokerface.R;
import com.kayluo.pokerface.adapter.LocationListAdapter;
import com.kayluo.pokerface.api.GetCityListRequestResponse;
import com.kayluo.pokerface.api.GetProvinceListRequestResponse;
import com.kayluo.pokerface.api.RequestResponseBase;
import com.kayluo.pokerface.dataModel.Province;

import java.util.List;

public class LocationCityListViewActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    private Toolbar mToolbar;
    private GetProvinceListRequestResponse requestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_city_list_view);
        mContext = this;
        initToolBar();


        requestResponse = new GetProvinceListRequestResponse(new RequestResponseBase.ResponseListener() {
            @Override
            public void onCompleted(Object data) {
                List<Province> list = (List<Province>)data;
                for(Province province : list)
                {
                   GetCityListRequestResponse getCityListRequestResponse = new GetCityListRequestResponse(province.ProvinceID, new RequestResponseBase.ResponseListener() {
                       @Override
                       public void onCompleted(Object data) {

                       }

                   });
                }
//                LocationListAdapter adapter = new LocationListAdapter(mContext,list);
//                initSubViews(adapter);

            }

        });


    }

    private void initToolBar(){

        mToolbar = (Toolbar) findViewById(R.id.location_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED,returnIntent);
                finish();
            }
        });

    }

    private void initSubViews(LocationListAdapter adapter)
    {
        mListView = (ListView) findViewById(R.id.city_header_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Province province = requestResponse.provinceList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("location",province.ProvinceName);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

}
