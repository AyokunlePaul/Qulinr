package app.gokada.qulinr.app_core.store.realmmodel;

import io.realm.RealmObject;

public class RealmWorkId extends RealmObject {

    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
