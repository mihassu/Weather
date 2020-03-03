package ru.mihassu.weather.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ru.mihassu.weather.R;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.network.RetrofitInit;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;
import ru.mihassu.weather.ui.cities.SearchFragment;
import ru.mihassu.weather.ui.weather.WeatherFragment;

public class MainActivity extends AppCompatActivity implements FragmentEventListener{

    public static final String API_KEY = "tOH3m9brk17Accoh8FAXbglaqkqK1jLd";
    public static final String LANGUAGE = "ru-ru";
    public static final String KEY_EXTRA = "key";

    private FragmentManager fragmentManager;
    private String currentKey;
//    private Button buttonSelectCity;
//    private TextView cityNameField;
//    private TextView temperatureField;
//    private TextView weatherTextField;
//    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFab();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            showWeatherFragment(savedInstanceState.getString(KEY_EXTRA));
        } else {
            showWeatherFragment(null);
        }

//        //Подписаться на LiveData - получить город из базы
//        viewModel.getCityData().observe(this, data -> {
//            if (data != null) {
//                //Установить в поле название города
//                cityNameField.setText(data.getCityName());
//                //Загрузить погоду
//                viewModel.loadWeather(data.getLocationKey(), API_KEY, LANGUAGE);
//                Log.d("Weather", "MainActivity - loadWeather() " + "key: " + data.getLocationKey());
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        });

//        //Подписаться на LiveData - получить погоду
//        viewModel.getWeatherData().observe(this, data -> {
//            Log.d("Weather", "MainActivity - gotWeather");
//
//            weatherTextField.setText(data.getWeatherText());
//            temperatureField.setText(String.valueOf(data.getTemperatureValue()));
//            progressBar.setVisibility(View.GONE);
//        });
    }

    @Override
    public void showWeatherFragment(String locationKey) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.container_main, WeatherFragment.newInstance(locationKey));
        ft.commit();
    }

    @Override
    public void showSearchFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.container_main, new SearchFragment());
        ft.addToBackStack("");
        ft.commit();
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
                fragmentManager.popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

}
