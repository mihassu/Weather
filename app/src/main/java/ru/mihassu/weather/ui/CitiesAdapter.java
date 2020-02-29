package ru.mihassu.weather.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mihassu.weather.R;
import ru.mihassu.weather.domain.model.City;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private ArrayList<City> citiesList = new ArrayList<>();

    void setCitiesList(ArrayList<City> citiesList) {
        this.citiesList = citiesList;
        notifyDataSetChanged();
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

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.cv_city_name);
            countryName = itemView.findViewById(R.id.cv_country_name);
        }

        public void bind(City city) {
            cityName.setText(city.getCityName());
            countryName.setText(city.getCountryName());
        }
    }
}
