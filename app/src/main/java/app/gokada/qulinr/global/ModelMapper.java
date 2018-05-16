package app.gokada.qulinr.global;

import app.gokada.qulinr.app_core.store.realmmodel.RealmToken;

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


}
