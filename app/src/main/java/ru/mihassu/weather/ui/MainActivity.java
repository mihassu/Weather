package ru.mihassu.weather.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.mihassu.weather.App;
import ru.mihassu.weather.R;
import ru.mihassu.weather.data.di.DataComponent;
import ru.mihassu.weather.ui.cities.SearchFragment;
import ru.mihassu.weather.ui.weather.WeatherViewModel;

public class MainActivity extends AppCompatActivity implements FragmentEventListener{

    public static final String API_KEY = "tOH3m9brk17Accoh8FAXbglaqkqK1jLd";
    public static final String LANGUAGE = "ru-ru";
    public static final String KEY_EXTRA = "key";
    private final String LOG_TAG = "Weather";
    private final String SEARCH_FRAGMENT = "SearchFragment";

    private FragmentManager fragmentManager;
    private WeatherViewModel viewModel;

    private String currentKey;
    private TextView cityNameField;
    private TextView temperatureField;
    private TextView weatherTextField;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFab();
        initViewModel();
        initViews();

        //Подписаться на LiveData - получить город из базы
        viewModel.getCityData().observe(this, data -> {
            if (data != null) {
                //Установить в поле название города
                cityNameField.setText(data.getCityName());
                Log.d(LOG_TAG, "Got city - setText(): " + data.getCityName());
                //Загрузить погоду
//                viewModel.loadWeather(data.getLocationKey(), API_KEY, LANGUAGE);
                viewModel.getWeatherFromDb(data.getLocationKey());
                Log.d(LOG_TAG, "loadWeather() " + "key: " + data.getLocationKey());
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        //Подписаться на LiveData - получить погоду
        viewModel.getWeatherData().observe(this, data -> {
            Log.d(LOG_TAG, "MainActivity - gotWeather + " + data.getTemperatureValue());
            cityNameField.setText(data.getCityName());
            weatherTextField.setText(data.getWeatherText());
            temperatureField.setText(String.valueOf(data.getTemperatureValue()));
            progressBar.setVisibility(View.GONE);
        });

        //Получить город из базы
        if (savedInstanceState != null) {
            viewModel.getCityFromDb(savedInstanceState.getString(KEY_EXTRA));
            Log.d(LOG_TAG, "savedInstanceState != null - getCityFromDb()");
        }
    }

    private void initViews() {
        fragmentManager = getSupportFragmentManager();
        cityNameField = getFragmetnWeather().getView().findViewById(R.id.city_name_field);
        weatherTextField = getFragmetnWeather().getView().findViewById(R.id.weather_text_field);
        temperatureField = getFragmetnWeather().getView().findViewById(R.id.temperature_field);
        progressBar = getFragmetnWeather().getView().findViewById(R.id.progressBar_weather);
    }

    @Override
    public void showWeatherFragment(String locationKey) {
        Log.d(LOG_TAG, "Got locationKey - getWeatherFromDb()");
        viewModel.getWeatherFromDb(locationKey);
        removeSearchFragment();
    }

    @Override
    public void showSearchFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.container_main, new SearchFragment(), SEARCH_FRAGMENT);
        ft.commit();
    }

    private void removeSearchFragment() {
        if (fragmentManager.findFragmentByTag(SEARCH_FRAGMENT) != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.remove(fragmentManager.findFragmentByTag(SEARCH_FRAGMENT));
            ft.commit();
        }
    }

    private void initViewModel() {
        DataComponent dataComponent = ((App) getApplication()).getDataComponent();
//        AccuWeatherApi api = RetrofitInit.newApiInstance();
//        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
//        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this,
                new ViewModelFactory(dataComponent.getRepository()))
                .get(WeatherViewModel.class);
    }

    private Fragment getFragmetnWeather() {
        return fragmentManager.findFragmentById(R.id.fragment_weather_static);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings :
                return true;

            case android.R.id.home :
                removeSearchFragment();
        }
        return super.onOptionsItemSelected(item);
    }

}
