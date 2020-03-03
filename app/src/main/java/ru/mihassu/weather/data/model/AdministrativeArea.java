package ru.mihassu.weather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {

    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;

    @SerializedName("LocalizedType")
    @Expose
    private String localizedType;


    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedType() {
        return localizedType;
    }

    public void setLocalizedType(String localizedType) {
        this.localizedType = localizedType;
    }
}
