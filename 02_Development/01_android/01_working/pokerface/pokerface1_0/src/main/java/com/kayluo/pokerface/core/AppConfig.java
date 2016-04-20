package com.kayluo.pokerface.core;

import com.kayluo.pokerface.dataModel.City;
import com.kayluo.pokerface.dataModel.Stage;
import com.kayluo.pokerface.dataModel.TutorEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxm170 on 2015/3/15.
 */
public class AppConfig {

    public List<String> gradeList;
    public List<City> cityList;
    public List<Stage> stageList;
    public City locationCity;

    public AppConfig()
    {
        locationCity = new City();
//        gradeList = new ArrayList<String>();
//        cityList = new ArrayList<City>();
//        stageList = new ArrayList<Stage>();
    }

}
