package ru.mihassu.weather.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.model.CityNetwork;
import ru.mihassu.weather.data.model.CityRealm;
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

                    //Добавить в базу
                    for (City city: cityList) {
                        realmProvider.insert(CityRealm.convertToRealmModel(city));
                    }

                    return cityList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
