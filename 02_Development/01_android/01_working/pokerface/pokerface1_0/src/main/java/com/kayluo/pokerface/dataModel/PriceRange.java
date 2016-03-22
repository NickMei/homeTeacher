package com.kayluo.pokerface.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nick on 2016-01-17.
 */
public class PriceRange {
    @SerializedName(value = "price_range")
    public String priceRange;
    @SerializedName(value = "price_min")
    public String priceMin;
    @SerializedName(value = "price_max")
    public String priceMax;
}
