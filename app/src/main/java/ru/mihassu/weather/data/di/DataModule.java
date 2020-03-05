package ru.mihassu.weather.data.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.repository.WeatherRepository;

@Module(includes = NetworkModule.class)
public class DataModule {

    @Provides
    @Singleton
    WeatherRepository repository(AccuWeatherApi api, DbProvider dbProvider) {
        return new WeatherRepository(api, dbProvider);
    }

    @Provides
    @Singleton
    DbProvider provideDbProvider() {
        return new RealmProvider();
    }
}
