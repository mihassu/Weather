package ru.mihassu.weather.data.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.mihassu.weather.data.model.CityRealm;
import ru.mihassu.weather.domain.model.City;

public class RealmProvider implements DbProvider<CityRealm, List<City>> {

    @Override
    public void insert(CityRealm data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans ->
                    trans.insertOrUpdate(data)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CityRealm data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans -> {
                trans.copyToRealmOrUpdate(data);
            });
        }
    }

    public void addNew(CityRealm data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans -> {
                CityRealm city = trans.where(CityRealm.class).equalTo("locationKey", data.getLocationKey()).findFirst();
                if (city != null) {
                    trans.insertOrUpdate(data);
                }
            });
        }
    }

    @Override
    public void delete(CityRealm data) {

    }

    @Override
    public List<City> select() {

        try (Realm realm = Realm.getDefaultInstance()) {
            final RealmResults<CityRealm> results =
                    realm.where(CityRealm.class).findAll();

            List<CityRealm> cities = realm.copyFromRealm(results);
            return cities.stream()
                    .map(city -> CityRealm.convertToCityModel(city))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return null;
        }
    }
}
