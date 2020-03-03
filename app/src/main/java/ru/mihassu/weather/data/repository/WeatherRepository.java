package ru.mihassu.weather.data.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.model.CityNetwork;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.model.Conditions;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.domain.model.City;
import ru.mihassu.weather.domain.repository.IWeatherRepository;

public class WeatherRepository implements IWeatherRepository {

    private AccuWeatherApi api;
    private DbProvider<CityRealm, List<City>> realmProvider;

    public WeatherRepository(AccuWeatherApi api, DbProvider<CityRealm, List<City>> realmProvider) {
        this.api = api;
        this.realmProvider = realmProvider;
    }

    public Single<ArrayList<City>> search(String apiKey, String searchText, String language) {

        return api.search(apiKey, searchText, language)
                .map(cityNetworkList -> {
                    ArrayList<City> cityList = (ArrayList<City>) cityNetworkList
                            .stream()
                            .map(cityNetwork -> CityNetwork.convertToCityModel(cityNetwork))
                            .collect(Collectors.toList());

//                    //Добавить в базу
//                    for (City city: cityList) {
//                        realmProvider.insert(CityRealm.convertToRealmModel(city));
//                    }

                    return cityList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public City getCityFromDbByKey(String locationKey) {

        return realmProvider.select()
                .stream()
                .filter(t -> t.getLocationKey().equals(locationKey))
                .findFirst()
                .orElse(null);
    }

    public Single<City> loadWeather(String locationKey, String apiKey, String language) {
        Log.d("Weather", "WeatherRepository - loadWeather()");

        return api.getWeather(locationKey, apiKey, language)
                .map(conditionsList -> {
                    Conditions conditions = conditionsList.get(0);
                    Log.d("Weather", "WeatherRepository - 1.locationKey: " + locationKey);
//                    Log.d("Weather", "WeatherRepository - " + conditions.getWeatherText() + ", " + conditions.getTemperature().getMetric().getValue());
                    CityRealm updatedCity = CityRealm.convertToRealmModel(getCityFromDbByKey(locationKey));
                    Log.d("Weather", "WeatherRepository - 2. " + updatedCity.getCityName());
                    updatedCity.setWeatherText(conditions.getWeatherText());
                    updatedCity.setTemperatureValue(conditions.getTemperature().getMetric().getValue());
                    updatedCity.setTemperatureUnit(conditions.getTemperature().getMetric().getUnit());
                    Log.d("Weather", "WeatherRepository - " + updatedCity.getCityName());
                    realmProvider.update(updatedCity);
                    return CityRealm.convertToCityModel(updatedCity);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addToDb(City city) {
        realmProvider.insert(new CityRealm(
                city.getLocationKey(),
                city.getCityName(),
                city.getCountryName(),
                city.getLocalizedName(),
                city.getLocalizedType(),
                "",
                0,
                ""
        ));
    }

    public List<City> loadCitiesFromDb() {
        return realmProvider.select();
    }
}
