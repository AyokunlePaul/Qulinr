package app.gokada.qulinr.app_core.store;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.app_core.store.realmmodel.RealmToken;
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

    public Observable<QulinrResponse<TimeTableResponse>> getTimetableForToday(String day){
        return onlineStore.getTimetableForToday(day);
    }

    public void cacheWorkId(String workId){
        offlineStore.cacheWorkId(workId);
    }

    public void cacheFoodType(String foodType){
        offlineStore.cacheFoodType(foodType);
    }

    public void cacheTimetable(TimeTableResponse response){
        offlineStore.cacheTimetable(response);
    }

    public TimeTableResponse getCachedTimetable(){
        return offlineStore.getCachedTimetable();
    }

    public String getCachedFoodType(){
        return offlineStore.getCachedFoodType();
    }

    public void deleteCountedValues(){
        offlineStore.deleteCountedValue();
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

    public void deleteToken(){
        offlineStore.deleteTypeOfClass(RealmToken.class);
    }

    public void deleteAll(){
        offlineStore.deleteAll();
    }

}
