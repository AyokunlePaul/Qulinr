package app.gokada.qulinr.app_core.store;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.store.localdatabase.RealmManager;
import app.gokada.qulinr.app_core.store.realmmodel.RealmToken;
import app.gokada.qulinr.global.ModelMapper;
import io.realm.RealmObject;

public class OfflineStore {

    private Context context;

    RealmManager realmManager;
    ModelMapper modelMapper;

    @Inject
    public OfflineStore(RealmManager realmManager, Context context, ModelMapper modelMapper){
        this.realmManager = realmManager;
        this.modelMapper = modelMapper;
        this.context = context;
    }

    public<T extends RealmObject> void cache(T object) {
        realmManager.save(object);
    }

    public<T extends RealmObject> void cacheFromAnotherthread(T object) {
        realmManager.saveAsCopy(object);
    }

    public void deleteTypeOfClass(Class<RealmToken> token) {
        //Log.i("Delete cached user", "Deleted");
        realmManager.deleteAllOf(token);
    }

    public String getToken() {
        List<RealmToken> realmToken = realmManager.findByClass(RealmToken.class);

        System.out.println("======= " + realmToken.size());
        if (realmToken.size() > 0) {
            return  modelMapper.map(realmToken.get(0));
        } else
            return null;
    }

}
