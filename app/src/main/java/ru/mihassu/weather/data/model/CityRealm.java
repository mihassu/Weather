package ru.mihassu.weather.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import ru.mihassu.weather.domain.model.City;

@RealmClass
public class CityRealm extends RealmObject {

    @PrimaryKey
    private String locationKey;
    private String cityName;
    private String countryName;
    private String localizedName;
    private String localizedType;
    private String weatherText;
    private double temperatureValue;
    private String temperatureUnit;

    public CityRealm(String locationKey, String cityName, String countryName, String localizedName,
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

    public CityRealm() {
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

    public static City convertToCityModel(CityRealm realmModel) {
        return new City(
                realmModel.getLocationKey(),
                realmModel.getCityName(),
                realmModel.getCountryName(),
                realmModel.getLocalizedName(),
                realmModel.getLocalizedType(),
                realmModel.getWeatherText(),
                realmModel.getTemperatureValue(),
                realmModel.getTemperatureUnit()
        );
    }

    public static CityRealm convertToRealmModel(City city) {
        return new CityRealm(
                city.getLocationKey(),
                city.getCityName(),
                city.getCountryName(),
                city.getLocalizedName(),
                city.getLocalizedType(),
                city.getWeatherText(),
                city.getTemperatureValue(),
                city.getTemperatureUnit()
        );
    }
}
