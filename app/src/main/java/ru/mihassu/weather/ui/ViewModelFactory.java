package ru.mihassu.weather.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.ui.cities.SearchFragmentViewModel;
import ru.mihassu.weather.ui.weather.WeatherFragmentViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WeatherRepository repository;

    public ViewModelFactory(WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass == WeatherFragmentViewModel.class) {
            return (T) new WeatherFragmentViewModel(repository);
        }

        if (modelClass == SearchFragmentViewModel.class) {
            return (T) new SearchFragmentViewModel(repository);
        }

        return null;
    }
}
