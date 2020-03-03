package ru.mihassu.weather.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ru.mihassu.weather.R;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.network.RetrofitInit;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;
import ru.mihassu.weather.ui.cities.CitiesActivity;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "tOH3m9brk17Accoh8FAXbglaqkqK1jLd";
    public static final String LANGUAGE = "ru-ru";
    public static final String KEY_EXTRA = "key";
    public static final int REQUEST_CODE = 1;
    private final String TAG = "MainActivity";

    private MainViewModel viewModel;

    private String currentKey;
    private Button buttonSelectCity;
    private TextView cityNameField;
    private TextView temperatureField;
    private TextView weatherTextField;
    private ProgressBar progressBar;
//    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initRouter(savedInstanceState);
        initToolbar();
        initFab();
        initViewModel();
        initViews();

        //Подписаться на LiveData - получить город из базы
        viewModel.getCityData().observe(this, data -> {
            if (data != null) {
                //Установить в поле название города
                cityNameField.setText(data.getCityName());
                //Загрузить погоду
                viewModel.loadWeather(data.getLocationKey(), API_KEY, LANGUAGE);
                Log.d("Weather", "MainActivity - loadWeather() " + "key: " + data.getLocationKey());
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        //Подписаться на LiveData - получить погоду
        viewModel.getWeatherData().observe(this, data -> {
            Log.d("Weather", "MainActivity - gotWeather");

            weatherTextField.setText(data.getWeatherText());
            temperatureField.setText(String.valueOf(data.getTemperatureValue()));
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initViews() {
        buttonSelectCity = findViewById(R.id.button_select_city);
        cityNameField = findViewById(R.id.city_name_field);
        temperatureField = findViewById(R.id.temperature_field);
        weatherTextField = findViewById(R.id.weather_text_field);
        progressBar = findViewById(R.id.progressBar_weather);

        //Запустить активити с быбором городов
        buttonSelectCity.setOnClickListener(v -> {
            Intent intent = new Intent(this, CitiesActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                //После того как получен ключ города - найти его в базе
                currentKey = data.getStringExtra(KEY_EXTRA);
                viewModel.getCityFromDb(currentKey);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initViewModel() {
        AccuWeatherApi api = RetrofitInit.newApiInstance();
        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository))
                .get(MainViewModel.class);
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
//        if (!router.handleBack()) {
//            super.onBackPressed();
//        }
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
//                router.handleBack();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void initRouter(Bundle savedInstanceState) {
//        ViewGroup container = findViewById(R.id.container_main);
//        router = Conductor.attachRouter(this, container, savedInstanceState);
//        if (!router.hasRootController()) {
//            router.setRoot(RouterTransaction.with(new WeatherFragmentC()));
//        }
//    }
}
