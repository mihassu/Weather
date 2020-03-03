package ru.mihassu.weather;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.mihassu.weather.data.di.DaggerDataComponent;
import ru.mihassu.weather.data.di.DataComponent;
import ru.mihassu.weather.data.di.DataModule;
import ru.mihassu.weather.data.di.NetworkModule;

public class App extends Application {

    private DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();

        dataComponent = DaggerDataComponent
                .builder()
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .build();
    }

    private void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("mycities.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
}
