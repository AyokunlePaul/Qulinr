package app.gokada.qulinr.app_core.store.realmmodel;

import io.realm.RealmObject;

/**
 * Created by eipeks on 3/23/18.
 */

public class RealmToken extends RealmObject {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
