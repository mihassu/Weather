package ru.mihassu.weather.data.di;

import android.content.pm.ApplicationInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mihassu.weather.data.network.AccuWeatherApi;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://dataservice.accuweather.com/";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    @Provides
    @Singleton
    AccuWeatherApi provideApi(Retrofit retrofit) {
        return retrofit.create(AccuWeatherApi.class);
    }
}
