package ru.mihassu.weather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("Metric")
    @Expose
    private Metric metric;

    public Metric getMetric() {
        return metric;
    }
}
