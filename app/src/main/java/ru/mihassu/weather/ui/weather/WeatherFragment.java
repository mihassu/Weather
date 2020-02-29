package ru.mihassu.weather.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mihassu.weather.R;
import ru.mihassu.weather.ui.cities.CitiesFragment;

public class WeatherFragment extends Fragment {

    private Button buttonSelect;
    private EditText city;
    private WeatherFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        buttonSelect = view.findViewById(R.id.button_select_city);
        city = view.findViewById(R.id.city_edit_text);
        return view;
    }


}
