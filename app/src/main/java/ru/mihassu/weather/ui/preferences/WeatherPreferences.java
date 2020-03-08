package ru.mihassu.weather.ui.preferences;

import android.content.Context;
import android.content.SharedPreferences;

//public class WeatherPreferences {
//
//    public final static String PREF_NAME = "preferences";
//    final static String LOCATION_KEY = "locationKey";
//
//    private SharedPreferences preferences;
//
//    public WeatherPreferences(Context context) {
//        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//    }
//
//    private SharedPreferences.Editor getEditor(){
//        return preferences.edit();
//    }
//
//    public void setLocationKey(String locationKey) {
//        getEditor().putString(LOCATION_KEY, locationKey).commit();
//    }
//
//    public String getLocationKey() {
//        return preferences.getString(LOCATION_KEY, "");
//    }
//}
