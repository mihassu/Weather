package ru.mihassu.weather.data.db;

import java.util.List;

import io.realm.Realm;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.domain.model.City;

public class RealmProvider implements DbProvider<CityRealm, List<City>> {

    @Override
    public void insert(CityRealm data) {
        try (Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction(trans ->
                    realm.insertOrUpdate(data)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CityRealm data) {

    }

    @Override
    public void delete(CityRealm data) {

    }

    @Override
    public List<City> select() {
        return null;
    }
}
