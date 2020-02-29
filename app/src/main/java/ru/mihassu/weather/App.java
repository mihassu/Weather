package ru.mihassu.weather;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("cities.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
