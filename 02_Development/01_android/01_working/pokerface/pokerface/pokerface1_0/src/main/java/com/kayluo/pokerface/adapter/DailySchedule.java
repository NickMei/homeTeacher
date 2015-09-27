package com.kayluo.pokerface.adapter;

import java.security.PublicKey;

/**
 * Created by cxm170 on 2015/3/26.
 */
public class DailySchedule {
    public String name;
    public String isMorningOK;
    public String isAfternoonOK;
    public String isNightOK;
    public DailySchedule(String name,String isMorningOK,String isAfternoonOK,String isNightOK)
    {
        this.name = name;
        this.isMorningOK = isMorningOK;
        this.isAfternoonOK = isAfternoonOK;
        this.isNightOK = isNightOK;

    }

}
