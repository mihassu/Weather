package ru.mihassu.weather.domain.model;

public class City {

    private String locationKey;
    private String cityName;
    private String countryName;

    public City(String locationKey, String cityName, String countryName) {
        this.locationKey = locationKey;
        this.cityName = cityName;
        this.countryName = countryName;
    }

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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
