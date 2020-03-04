package ru.mihassu.weather.ui.weather;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<City> cityData = new MutableLiveData<>();
    private MutableLiveData<City> weatherData = new MutableLiveData<>();
    private WeatherRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<City> getCityData() {
        return cityData;
    }

    public MutableLiveData<City> getWeatherData() {
        return weatherData;
    }

    public void getCityFromDb(String locationKey) {
        cityData.setValue(repository.getCityFromDbByKey(locationKey));
    }

    public void loadWeather(String locationKey, String apiKey, String language) {
        Log.d("Weather", "MainViewModel - loadWeather()");
        compositeDisposable.add(repository
                        .loadWeather(locationKey, apiKey, language)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> cityData.setValue(data), throwable -> Log.d("Weather", "Error loadWeather"))
        );
    }

    public void getWeatherFromDb(String locationKey) {
        weatherData.setValue(repository.getCityFromDbByKey(locationKey));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
