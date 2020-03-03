package ru.mihassu.weather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric {

    @SerializedName("Value")
    @Expose
    private double value;

    @SerializedName("Unit")
    @Expose
    private String unit;

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
