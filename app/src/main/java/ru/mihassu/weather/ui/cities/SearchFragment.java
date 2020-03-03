package ru.mihassu.weather.ui.cities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.mihassu.weather.App;
import ru.mihassu.weather.R;
import ru.mihassu.weather.data.db.DbProvider;
import ru.mihassu.weather.data.db.RealmProvider;
import ru.mihassu.weather.data.di.DataComponent;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.data.network.AccuWeatherApi;
import ru.mihassu.weather.data.network.RetrofitInit;
import ru.mihassu.weather.data.repository.WeatherRepository;
import ru.mihassu.weather.domain.model.City;
import ru.mihassu.weather.ui.FragmentEventListener;
import ru.mihassu.weather.ui.ViewModelFactory;

import static ru.mihassu.weather.ui.MainActivity.API_KEY;
import static ru.mihassu.weather.ui.MainActivity.LANGUAGE;

public class SearchFragment extends Fragment {

    private SearchFragmentViewModel viewModel;
    private ProgressBar progressBar;
    private CitiesAdapter citiesAdapter;
    private EditText cityField;
    private Button buttonFind;
    private FragmentEventListener activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            activity = (FragmentEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Context can not implements");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();

        //Подписаться на LiveData - результат поиска
        viewModel.getSearchData().observe(this, data -> {
            if (data.isEmpty()) {
                Snackbar.make(cityField, R.string.not_found, Snackbar.LENGTH_LONG).show();
            }
            citiesAdapter.setCitiesList(data);
            progressBar.setVisibility(View.GONE);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        initRecyclerView(view);
        //получить города из базы
        viewModel.loadCitiesFromDb();
        return view;
    }

    private void initViews(View view) {
        cityField = view.findViewById(R.id.et_enter_city);
        progressBar = view.findViewById(R.id.progressBar_search);
        buttonFind = view.findViewById(R.id.button_find);

        //Найти город (в сети)
        buttonFind.setOnClickListener(v -> {
                    viewModel.search(API_KEY, cityField.getText().toString(), LANGUAGE);
                    progressBar.setVisibility(View.VISIBLE);
                }
        );
    }

    private void initViewModel() {
        DataComponent dataComponent = ((App) getActivity().getApplication()).getDataComponent();
//        AccuWeatherApi api = RetrofitInit.newApiInstance();
//        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
//        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this,
                new ViewModelFactory(dataComponent.getRepository()))
                .get(SearchFragmentViewModel.class);

    }

    private void initRecyclerView(View view) {
        RecyclerView rvCities = view.findViewById(R.id.rv_cities);
        citiesAdapter = new CitiesAdapter();
        citiesAdapter.setListener(this::showWeather);
        rvCities.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCities.setAdapter(citiesAdapter);
    }

    private void showWeather(City city) {
        //Добавить в базу
        viewModel.addToDb(city);
        activity.showWeatherFragment(city.getLocationKey());
    }
}
