package app.gokada.qulinr.screens.splash.viewmodel;

import android.util.Log;

import java.util.Calendar;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.view.CoreVM;
import app.gokada.qulinr.global.Day;
import rx.Subscriber;

public class SplashVM extends CoreVM {

    private DataStore dataStore;

    @Inject
    public SplashVM(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public void getTimetableForToday(final OnTimetableRetrievedCallback onTimetableRetrievedCallback){
        Day.DAY currentDay = getCurrentDay();
        dataStore.getTimetableForToday(currentDay.toString()).subscribe(new Subscriber<QulinrResponse<TimeTableResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onTimetableRetrievedCallback.onErrorOccurred(e);
            }

            @Override
            public void onNext(QulinrResponse<TimeTableResponse> timeTableResponseQulinrResponse) {
                onTimetableRetrievedCallback.onTimetableRetrieved(timeTableResponseQulinrResponse.getData());
            }
        });
    }

    public void getFullTimetable(final OnFullTimeTableRetrievedCallback onFullTimeTableRetrievedCallback){
        dataStore.getFullTimetable().subscribe(new Subscriber<QulinrResponse<FullTimeTableResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onFullTimeTableRetrievedCallback.onErrorOccurred(e);
            }

            @Override
            public void onNext(QulinrResponse<FullTimeTableResponse> fullTimeTableResponseQulinrResponse) {
                onFullTimeTableRetrievedCallback.onFullTimeTableRetrieved(fullTimeTableResponseQulinrResponse.getData());
            }
        });
    }

    public void cacheTimetable(TimeTableResponse response){
        dataStore.cacheTimetable(response);
    }

    public void cacheFullTimeTable(FullTimeTableResponse response){
        dataStore.cacheFullTimetable(response);
    }

    private Day.DAY getCurrentDay(){
        Log.i("DAY OF WEEK", "" + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            default:
            case Calendar.SUNDAY:
                return Day.DAY.SUNDAY;
            case Calendar.MONDAY:
                return Day.DAY.MONDAY;
            case Calendar.TUESDAY:
                return Day.DAY.TUESDAY;
            case Calendar.WEDNESDAY:
                return Day.DAY.WEDNESDAY;
            case Calendar.THURSDAY:
                return Day.DAY.THURSDAY;
            case Calendar.FRIDAY:
                return Day.DAY.FRIDAY;
            case Calendar.SATURDAY:
                return Day.DAY.SATURDAY;
        }
    }

    public interface OnTimetableRetrievedCallback{
        void onTimetableRetrieved(TimeTableResponse response);
        void onErrorOccurred(Throwable throwable);
    }

    public interface OnFullTimeTableRetrievedCallback{
        void onFullTimeTableRetrieved(FullTimeTableResponse response);
        void onErrorOccurred(Throwable throwable);
    }
}
