package ru.mihassu.weather.ui.cities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mihassu.weather.R;
import ru.mihassu.weather.domain.model.City;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private List<City> citiesList = new ArrayList<>();
    private OnCityClickListener listener;

    void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
        notifyDataSetChanged();
    }

    public void setListener(OnCityClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        return new CitiesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.bind(citiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView cityName;
        private TextView countryName;
        private TextView localizedName;
        private TextView localizedType;
        private String locationKey;
        private City city;

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cv_city_name);
            countryName = itemView.findViewById(R.id.cv_country_name);
            localizedName = itemView.findViewById(R.id.cv_localizedName);
            localizedType = itemView.findViewById(R.id.cv_localizedType);
            itemView.setOnClickListener(v -> listener.onClick(city));
        }

        public void bind(City city) {
            cityName.setText(city.getCityName());
            countryName.setText(city.getCountryName());
            localizedName.setText(city.getLocalizedName());
            localizedType.setText(city.getLocalizedType());
            locationKey = city.getLocationKey();
            this.city = city;
        }
    }

    interface OnCityClickListener {
//        void onClick(String locationKey);
        void onClick(City city);
    }
}
