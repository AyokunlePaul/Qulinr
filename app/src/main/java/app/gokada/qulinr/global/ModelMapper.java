package app.gokada.qulinr.global;

import app.gokada.qulinr.app_core.store.realmmodel.RealmToken;
import app.gokada.qulinr.app_core.store.realmmodel.RealmWorkId;

/**
 * Created by knightbenax on 3/8/18.
 */

public class ModelMapper {

    public RealmToken map(String rideId){
        RealmToken ride = new RealmToken();
        ride.setToken(rideId);

        return ride;
    }

    public String map(RealmToken ride){
        return ride.getToken();
    }

    public RealmWorkId mapIdToRealmWorkId(String realmId){
        RealmWorkId workId = new RealmWorkId();
        workId.setWorkId(realmId);

        return workId;
    }

    public String mapRealmWorkIdToString(RealmWorkId workId){
        return workId.getWorkId();
    }

}
