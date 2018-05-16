package app.gokada.qulinr.app_core.store.realmmodel;

import io.realm.RealmObject;

public class RealmCounter extends RealmObject {
    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
