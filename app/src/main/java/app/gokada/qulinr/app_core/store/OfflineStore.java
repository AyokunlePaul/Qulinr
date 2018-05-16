package app.gokada.qulinr.app_core.store;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.store.localdatabase.RealmManager;
import app.gokada.qulinr.app_core.store.realmmodel.RealmToken;
import app.gokada.qulinr.app_core.store.realmmodel.RealmWorkId;
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

    public void deleteAll(){
        realmManager.deleteAll();
    }

//    public void deleteWorkId(Class<RealmWorkId> workIdClass){
//        realmManager.deleteAllOf(workIdClass);
//    }

    public void deleteWorkId(){
        RealmWorkId workId = modelMapper.mapIdToRealmWorkId(getCachedWorkId());
        realmManager.delete(workId);
    }

    public void cacheToken(String token){
        RealmToken realmToken = modelMapper.map(token);
        cache(realmToken);
    }

    public String getToken() {
        List<RealmToken> realmToken = realmManager.findByClass(RealmToken.class);

        System.out.println("======= " + realmToken.size());
        if (realmToken.size() > 0) {
            return  modelMapper.map(realmToken.get(0));
        } else
            return null;
    }

    public String getCachedWorkId(){
        List<RealmWorkId> realmWorkIds = realmManager.findByClass(RealmWorkId.class);

        if (realmWorkIds.size() > 0){
            return modelMapper.mapRealmWorkIdToString(realmWorkIds.get(0));
        } else {
            return null;
        }
    }

    public void cacheWorkId(String workId){
        RealmWorkId realmWorkId = modelMapper.mapIdToRealmWorkId(workId);
        cache(realmWorkId);
    }

}
