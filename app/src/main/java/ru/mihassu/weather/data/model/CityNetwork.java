package ru.mihassu.weather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.mihassu.weather.domain.model.City;

public class CityNetwork {

    @SerializedName("Key")
    @Expose
    private String locationKey;

    @SerializedName("LocalizedName")
    @Expose
    private String cityName;

    @SerializedName("Country")
    @Expose
    private Country country;

    @SerializedName("AdministrativeArea")
    @Expose
    private AdministrativeArea administrativeArea;



    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCountryName() {
        return country.getCountryName();
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public static City convertToCityModel (CityNetwork networkModel) {
        return new City(
                networkModel.getLocationKey(),
                networkModel.getCityName(),
                networkModel.getCountryName(),
                networkModel.getAdministrativeArea().getLocalizedName(),
                networkModel.getAdministrativeArea().getLocalizedType(),
                "",
                0,
                ""
        );
    }
}
