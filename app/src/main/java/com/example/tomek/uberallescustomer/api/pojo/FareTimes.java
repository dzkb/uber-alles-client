package com.example.tomek.uberallescustomer.api.pojo;

import com.google.gson.annotations.SerializedName;

public class FareTimes {

    @SerializedName("min")
    private Integer min;
    @SerializedName("max")
    private Integer max;
    @SerializedName("avg")
    private Double avg;

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Double getAvg() {
        return avg;
    }
}
