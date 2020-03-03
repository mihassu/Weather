package ru.mihassu.weather.data.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mihassu.weather.data.model.CityNetwork;
import ru.mihassu.weather.data.model.Conditions;

public interface AccuWeatherApi {

    @GET("locations/v1/search")
    Single<ArrayList<CityNetwork>>search(
            @Query("apikey")String apiKey,
            @Query("q")String searchText,
            @Query("language") String language
    );



    @GET("currentconditions/v1/{locationKey}")
    Single<List<Conditions>> getWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey")String apiKey,
            @Query("language") String language
    );
}
