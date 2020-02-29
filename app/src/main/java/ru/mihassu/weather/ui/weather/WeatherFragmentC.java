package ru.mihassu.weather.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;

import ru.mihassu.weather.R;
import ru.mihassu.weather.ui.MainViewModel;
import ru.mihassu.weather.ui.cities.CitiesFragment;

public class WeatherFragmentC extends Controller  {

    private Button buttonSelect;
    private EditText city;
    private WeatherFragmentViewModel viewModel;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        buttonSelect = view.findViewById(R.id.button_select_city);
        city = view.findViewById(R.id.city_edit_text);
        buttonSelect.setOnClickListener(v ->
                getRouter().pushController(RouterTransaction.with(new CitiesFragment(city.getText().toString()))));
        return view;
    }

}
