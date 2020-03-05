package ru.mihassu.weather.ui.cities;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;

public class SearchFragmentViewModel extends ViewModel {

    private MutableLiveData<List<City>> searchData = new MutableLiveData<>();
    private WeatherRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchFragmentViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<City>> getSearchData() {
        return searchData;
    }


    public void search(String apiKey, String searchText, String language) {
        compositeDisposable.add(
                repository
                        .search(apiKey, searchText, language)
                        .subscribe(data -> searchData.setValue(data), System.out::println)
        );
    }

    public void addToDb(City city) {
        repository.addToDb(city);
    }

    public void loadCitiesFromDb() {
        searchData.setValue(repository.loadCitiesFromDb());
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onStart() {
//        loadCitiesFromDb();
//    }
}
