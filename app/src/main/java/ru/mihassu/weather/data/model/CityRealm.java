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

    public CityRealm(String locationKey, String cityName, String countryName) {
        this.locationKey = locationKey;
        this.cityName = cityName;
        this.countryName = countryName;
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


    public static City convertToCityModel(CityRealm realmModel) {
        return new City(
                realmModel.getLocationKey(),
                realmModel.getCityName(),
                realmModel.getCountryName()
        );
    }

    public static CityRealm convertToRealmModel(City city) {
        return new CityRealm(
                city.getLocationKey(),
                city.getCityName(),
                city.getCountryName()
        );
    }
}
