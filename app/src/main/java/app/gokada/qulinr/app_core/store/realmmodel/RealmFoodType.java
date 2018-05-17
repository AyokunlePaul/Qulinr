package app.gokada.qulinr.app_core.store.realmmodel;

import io.realm.RealmObject;

public class RealmFoodType extends RealmObject {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
