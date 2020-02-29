package ru.mihassu.weather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conditions {

    @SerializedName("WeatherText")
    @Expose
    private String weatherText;

    @SerializedName("PrecipitationType")
    @Expose
    private String precipitationType;

    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;

    public String getWeatherText() {
        return weatherText;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}
