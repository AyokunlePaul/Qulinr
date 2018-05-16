package app.gokada.qulinr.app_core.store;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import rx.Observable;

public class DataStore {

    private OnlineStore onlineStore;
    private OfflineStore offlineStore;

    @Inject
    public DataStore(OnlineStore onlineStore, OfflineStore offlineStore){
        this.offlineStore = offlineStore;
        this.onlineStore = onlineStore;
    }

    public Observable<QulinrResponse> createMenu(CreateMenuRequest menuRequest){
        return onlineStore.createMenu(menuRequest);
    }

    public Observable<QulinrResponse> notifySlack(NotifySlackRequest slackRequest){
        return onlineStore.notifySlack(slackRequest);
    }

    public void cacheWorkId(String workId){
        offlineStore.cacheWorkId(workId);
    }

    public String getCachedWorkId(){
        return offlineStore.getCachedWorkId();
    }

    public void cacheToken(String token){
        offlineStore.cacheToken(token);
    }

    public String getCachedToken(){
        return offlineStore.getToken();
    }

    public void deleteWorkId(){
        offlineStore.deleteWorkId();
    }

    public void deleteAll(){
        offlineStore.deleteAll();
    }

}
