package ru.mihassu.weather.ui.cities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluelinelabs.conductor.Controller;

import ru.mihassu.weather.R;

public class CitiesFragment extends Controller {

    public final String CITY_EXTRA = "city";
    private TextView cityField;
    private String city;

    public CitiesFragment(String city) {
        this.city = city;
        getArgs().putString(CITY_EXTRA, city);
    }

    public CitiesFragment(@Nullable Bundle args) {
        super(args);
        city = args != null ? args.getString(CITY_EXTRA) : null;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_cities, container,false);
        cityField = view.findViewById(R.id.city_field);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        cityField.setText(city);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cityField = null;
    }
}
