package ru.mihassu.weather.data.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.mihassu.weather.data.repository.WeatherRepository;

@Component(modules = DataModule.class)

@Singleton
public interface DataComponent {

    WeatherRepository getRepository();
}
