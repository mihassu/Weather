package ru.mihassu.weather.domain.model;

public class City {

    private String locationKey;
    private String cityName;
    private String countryName;
    private String localizedName;
    private String localizedType;
    private String weatherText;
    private double temperatureValue;
    private String temperatureUnit;

    public City(String locationKey, String cityName, String countryName, String localizedName,
                String localizedType, String weatherText, double temperatureValue, String temperatureUnit) {
        this.locationKey = locationKey;
        this.cityName = cityName;
        this.countryName = countryName;
        this.localizedName = localizedName;
        this.localizedType = localizedType;
        this.weatherText = weatherText;
        this.temperatureValue = temperatureValue;
        this.temperatureUnit = temperatureUnit;
    }

//    public City(String locationKey, String cityName, String countryName, String localizedName, String localizedType) {
//        this.locationKey = locationKey;
//        this.cityName = cityName;
//        this.countryName = countryName;
//        this.localizedName = localizedName;
//        this.localizedType = localizedType;
//    }

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

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public double getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(double temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
}
