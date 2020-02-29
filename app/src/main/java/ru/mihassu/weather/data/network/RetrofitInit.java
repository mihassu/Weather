package ru.mihassu.weather.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {

    private static final String BASE_URL = "https://dataservice.accuweather.com/";

    private static AccuWeatherApi api;

    public static synchronized AccuWeatherApi newApiInstance() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(AccuWeatherApi.class);
        }
        return api;
    }
}
