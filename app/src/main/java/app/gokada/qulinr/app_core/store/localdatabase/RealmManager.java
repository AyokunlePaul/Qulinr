package app.gokada.qulinr.app_core.store.localdatabase;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public class RealmManager {
    private final Realm realm;

    public RealmManager(Realm realm) {
        this.realm = realm;
    }

    public void close() {
        realm.close();
    }

    public <T extends RealmObject> List<T> findByClass(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public <T extends RealmObject> List<T> findByClassFromAnotherThread(final Class<T> clazz) {
       Realm mRealm = Realm.getDefaultInstance();
       return mRealm.where(clazz).findAll();
    }

    public void delete(final RealmObject realmObject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmObject.deleteFromRealm();
            }
        });
    }

    public void save(final List<RealmObject> objects) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RealmObject object : objects) {
                    realm.insert(object);
                }
            }
        });
    }

    public void saveAsCopy(final List<RealmObject> objects) {
        final Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RealmObject object : objects) {
                    mRealm.insert(object);
                }
            }
        });
    }

    public Realm getRealm() {
        return realm;
    }

    public void save(RealmObject object) {
        save(Collections.singletonList(object));
    }

    public void saveAsCopy(RealmObject object) {
        saveAsCopy(Collections.singletonList(object));
    }

    public void saveOrUpdate(RealmObject object) {
        saveOrUpdate(Collections.singletonList(object));
    }

    public void saveOrUpdate(final List<RealmObject> realmObjects) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RealmObject object : realmObjects) {
                    realm.insertOrUpdate(object);
                }
            }
        });
    }

    public <T extends RealmObject> void update(final T realObject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realObject);
            }
        });
    }

    public <T extends RealmObject> T findById(Class<T> clazz, String idFieldKey, int id) {
        return realm.where(clazz).equalTo(idFieldKey, id).findFirst();
    }

    public <T extends RealmObject> T findById(Class<T> clazz, String idFieldKey, Long id) {
        return realm.where(clazz).equalTo(idFieldKey, id).findFirst();
    }

    public void deleteAll() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public <T extends RealmObject> void deleteAllOf(Class<T> clazz) {
        final List<T> realmList = realm.where(clazz).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RealmObject object : realmList) {
                    object.deleteFromRealm();
                }
            }
        });
    }

    public <T extends RealmObject> RealmQuery<T> where(Class<T> clazz) {
        return realm.where(clazz);
    }
}
