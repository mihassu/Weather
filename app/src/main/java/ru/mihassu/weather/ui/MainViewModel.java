package ru.mihassu.weather.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<City>> searchData = new MutableLiveData<>();
    private WeatherRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    public void search(String apiKey, String searchText, String language) {
        compositeDisposable.add(
                repository
                        .search(apiKey, searchText, language)
                        .subscribe(data -> searchData.setValue(data), System.out::println)
        );
    }

    public MutableLiveData<ArrayList<City>> getSearchData() {
        return searchData;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
