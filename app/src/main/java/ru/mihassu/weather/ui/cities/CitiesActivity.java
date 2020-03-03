package ru.mihassu.weather.ui.cities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static ru.mihassu.weather.ui.MainActivity.LANGUAGE;
import static ru.mihassu.weather.ui.MainActivity.API_KEY;
import static ru.mihassu.weather.ui.MainActivity.KEY_EXTRA;

import ru.mihassu.weather.R;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.network.RetrofitInit;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;
import ru.mihassu.weather.ui.ViewModelFactory;


public class CitiesActivity extends AppCompatActivity {

    private CitiesViewModel viewModel;
    private ProgressBar progressBar;
    private CitiesAdapter citiesAdapter;
    private EditText cityField;
    private Button buttonFind;

    private final String TAG = "CitiesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        initViewModel();
        initRecyclerView();
        initViews();

        //Подписаться на LiveData - результат поиска
        viewModel.getSearchData().observe(this, data -> {
            if (data.isEmpty()) {
                Snackbar.make(cityField, R.string.not_found, Snackbar.LENGTH_LONG).show();
            }
            citiesAdapter.setCitiesList(data);
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initViews() {
        cityField = findViewById(R.id.et_enter_city);
        progressBar = findViewById(R.id.progressBar_search);
        buttonFind = findViewById(R.id.button_find);

        //Найти город (в сети)
        buttonFind.setOnClickListener(v -> {
                    viewModel.search(API_KEY, cityField.getText().toString(), LANGUAGE);
                    progressBar.setVisibility(View.VISIBLE);
                }
        );
    }

    private void initRecyclerView() {
        RecyclerView rvCities = findViewById(R.id.rv_cities);
        citiesAdapter = new CitiesAdapter();
        citiesAdapter.setListener(this::sendResult);
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setAdapter(citiesAdapter);
    }

    private void initViewModel() {
        AccuWeatherApi api = RetrofitInit.newApiInstance();
        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository))
                .get(CitiesViewModel.class);
        getLifecycle().addObserver(viewModel);
    }

    private void sendResult(City city) {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXTRA, city.getLocationKey());
        setResult(Activity.RESULT_OK, intent);
        //Добавить в базу
        viewModel.addToDb(city);
        finish();
    }
}
