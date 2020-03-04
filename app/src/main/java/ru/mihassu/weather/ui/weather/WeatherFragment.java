package ru.mihassu.weather.ui.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.mihassu.weather.R;

import ru.mihassu.weather.ui.FragmentEventListener;

public class WeatherFragment extends Fragment {

    private Button buttonSelectCity;
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        buttonSelectCity = view.findViewById(R.id.button_select_city);
        buttonSelectCity.setOnClickListener(v -> activity.showSearchFragment());
    }

}
