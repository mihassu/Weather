package ru.mihassu.weather.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import ru.mihassu.weather.R;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.network.RetrofitInit;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "tOH3m9brk17Accoh8FAXbglaqkqK1jLd";
    private final String LANGUAGE = "ru-ru";
    private final String TAG = "MainActivity";
    private MainViewModel viewModel;
//    private Router router;
    private CitiesAdapter citiesAdapter;

    private Button buttonSelect;
    private EditText city;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initRouter(savedInstanceState);
        initToolbar();
        initFab();
        initViewModel();
        initRecyclerView();

        viewModel.getSearchData().observe(this, data -> {
                citiesAdapter.setCitiesList(data);
                progressBar.setVisibility(View.GONE);
                for (City city: data) {
                    Log.d(TAG, city.getCityName());
                }
        });

        buttonSelect = findViewById(R.id.button);
        city = findViewById(R.id.city);
        progressBar = findViewById(R.id.progressBar);


        buttonSelect.setOnClickListener(v -> {
                    viewModel.search(API_KEY, city.getText().toString(), LANGUAGE);
                    progressBar.setVisibility(View.VISIBLE);
                }

        );

    }

    private void initRecyclerView() {
        RecyclerView rvCities = findViewById(R.id.rv_cities);
        citiesAdapter = new CitiesAdapter();
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setAdapter(citiesAdapter);
    }



    private void initViewModel() {
        AccuWeatherApi api = RetrofitInit.newApiInstance();
        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this, new MainViewModelFactory(repository))
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
