package com.kayluo.pokerface.common;

/**
 * Created by cxm170 on 11/15/2015.
 */
public enum EActivityRequestCode {

    DISPLAY_LOGIN(0),
    SELECT_LOCATION(1),
    SHOW_USER_DETAIL(2),
    SELECT_COURSE(3),
    TUTOR_LIST_FILTER(4),
    TUTOR_LIST(5),
    SETTINGS(6),
    CHANGE_PASSWORD(7),
    DISPLAY_TUTOR_DETAIL(8),
    SEARCH_RESULTS(9),
    BOOKING_REMAKRS(10),
    TEACHING_ADDRESS(11);


    private final int value;

    private EActivityRequestCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static EActivityRequestCode[] allValues = values();
    public static EActivityRequestCode fromOrdinal(int n) {return allValues[n];}

}
