package ru.mihassu.weather.ui.weather;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import static ru.mihassu.weather.ui.MainActivity.LANGUAGE;
import static ru.mihassu.weather.ui.MainActivity.API_KEY;
import static ru.mihassu.weather.ui.MainActivity.KEY_EXTRA;


public class WeatherFragment extends Fragment {

    private Button buttonSelectCity;
    private TextView cityNameField;
    private TextView temperatureField;
    private TextView weatherTextField;
    private ProgressBar progressBar;
    private WeatherFragmentViewModel viewModel;
    private FragmentEventListener activity;

    public static WeatherFragment newInstance(String locationKey) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_EXTRA, locationKey);
        fragment.setArguments(bundle);
        return fragment;
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initViews(view);

        //Получить город из базы по ключу
        String locationKey = getArguments().getString(KEY_EXTRA, null);
        if (locationKey != null) {
            viewModel.getCityFromDb(locationKey);
        }

        return view;
    }

    private void initViews(View view) {
        buttonSelectCity = view.findViewById(R.id.button_select_city);
        cityNameField = view.findViewById(R.id.city_name_field);
        temperatureField = view.findViewById(R.id.temperature_field);
        weatherTextField = view.findViewById(R.id.weather_text_field);
        progressBar = view.findViewById(R.id.progressBar_weather);

        buttonSelectCity.setOnClickListener(v -> activity.showSearchFragment());
    }

    private void initViewModel() {
        DataComponent dataComponent = ((App) getActivity().getApplication()).getDataComponent();
//        AccuWeatherApi api = RetrofitInit.newApiInstance();
//        DbProvider<CityRealm, List<City>> realmProvider = new RealmProvider();
//        WeatherRepository repository = new WeatherRepository(api, realmProvider);
        viewModel = new ViewModelProvider(this,
                new ViewModelFactory(dataComponent.getRepository()))
                .get(WeatherFragmentViewModel.class);
    }
}
