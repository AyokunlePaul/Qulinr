package app.gokada.qulinr.screens.home;

import java.util.Calendar;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.view.CoreVM;
import rx.Subscriber;

public class HomeActivityVM extends CoreVM {

    private DataStore dataStore;

    @Inject
    public HomeActivityVM(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public void createMenu(CreateMenuRequest menuRequest, final OnMenuCreatedCallback onMenuCreatedCallback){
        dataStore.createMenu(menuRequest).subscribe(new Subscriber<QulinrResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onMenuCreatedCallback.onMenuCreated(null, e);
            }

            @Override
            public void onNext(QulinrResponse qulinrResponse) {
                onMenuCreatedCallback.onMenuCreated(qulinrResponse, null);
            }
        });
    }

    public void notifySlack(final OnSlackNotifiedCallback onSlackNotifiedCallback){
        dataStore.notifySlack(buildRequestFromToken()).subscribe(new Subscriber<QulinrResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onSlackNotifiedCallback.onSlackNotified(null, e);
            }

            @Override
            public void onNext(QulinrResponse response) {
                onSlackNotifiedCallback.onSlackNotified(response, null);
            }
        });
    }

    public void deleteToken(){
        dataStore.deleteToken();
    }

    public void cacheToken(String token){
        dataStore.cacheToken(token);
    }

    public void cacheWorkId(String workId){
        dataStore.cacheWorkId(workId);
    }

    private NotifySlackRequest buildRequestFromToken(){
        NotifySlackRequest request = new NotifySlackRequest();
        request.setToken(getCachedToken());

        return request;
    }

    public String getCachedToken(){
        return dataStore.getCachedToken();
    }

    public void cacheFoodType(String foodType){
        dataStore.cacheFoodType(foodType);
    }

    public String getCachedFoodType(){
        return dataStore.getCachedFoodType();
    }

    public TimeTableResponse getCachedTimetable(){
        return dataStore.getCachedTimetable();
    }

    public String[] getListOfFoodFromFullTimeTableResponse(){
        FullTimeTableResponse response = dataStore.getCachedFullTimeTable();
        String[] foodArray = new String[21];
        foodArray[0] = response.getSunday().getBreakfast();
        foodArray[1] = response.getSunday().getLunch();
        foodArray[2] = response.getSunday().getDinner();
        foodArray[3] = response.getMonday().getBreakfast();
        foodArray[4] = response.getMonday().getLunch();
        foodArray[5] = response.getMonday().getDinner();
        foodArray[6] = response.getTuesday().getBreakfast();
        foodArray[7] = response.getTuesday().getLunch();
        foodArray[8] = response.getTuesday().getDinner();
        foodArray[9] = response.getWednesday().getBreakfast();
        foodArray[10] = response.getWednesday().getLunch();
        foodArray[11] = response.getWednesday().getDinner();
        foodArray[12] = response.getThursday().getBreakfast();
        foodArray[13] = response.getThursday().getLunch();
        foodArray[14] = response.getThursday().getDinner();
        foodArray[15] = response.getFriday().getBreakfast();
        foodArray[16] = response.getFriday().getLunch();
        foodArray[17] = response.getFriday().getDinner();
        foodArray[18] = response.getSaturday().getBreakfast();
        foodArray[19] = response.getSaturday().getLunch();
        foodArray[20] = response.getSaturday().getDinner();

        return foodArray;
    }

    public interface OnMenuCreatedCallback {
        void onMenuCreated(QulinrResponse response, Throwable throwable);
    }

    public interface OnSlackNotifiedCallback {
        void onSlackNotified(QulinrResponse response, Throwable throwable);
    }
}
