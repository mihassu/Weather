package ru.mihassu.weather.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mihassu.weather.data.repository.WeatherRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WeatherRepository repository;

    public MainViewModelFactory(WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(repository);
        }
        return null;
    }
}
