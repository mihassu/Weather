package ru.mihassu.weather.ui;

import ru.mihassu.weather.domain.model.City;

public interface FragmentEventListener {

    void showWeatherFragment(City city);
    void showSearchFragment();
}
