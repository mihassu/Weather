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


    public void getWeatherFromDb(String locationKey) {
        weatherData.postValue(repository.getCityFromDbByKey(locationKey));
    }

    public void loadWeather(City city, String apiKey, String language) {
        Log.d("Weather", "MainViewModel - loadWeather()");
        compositeDisposable.add(repository
                        .loadWeather(city, apiKey, language)
                        .subscribe(data -> weatherData.setValue(data), throwable -> Log.d("Weather", "Error loadWeather:" + throwable.getMessage()))
        );
    }

    public void addToDb(City city) {
        repository.addToDb(city);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
