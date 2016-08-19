package com.example.wind.meizhi.net;

import com.example.wind.meizhi.mvp.model.ZhihuStory;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * Created by Summers on 2016/8/16.
 */
public class DB {
    public static void saveOrUpdate(Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
    }

    public static <T extends RealmObject> RealmResults<T> findAll(Realm realm, Class<T> realmObjectClass) {
        return realm.where(realmObjectClass).findAll();
    }

    public static <T extends RealmObject> T getById(Realm realm, int id, Class<T> realmObjectClass) {
        return realm.where(realmObjectClass).equalTo("id",id).findFirst();
    }
}
