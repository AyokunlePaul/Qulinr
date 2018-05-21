package app.gokada.qulinr.app_core.store.realmmodel;

import com.google.gson.annotations.SerializedName;

import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import io.realm.RealmObject;

public class RealmFullTimeTable extends RealmObject {

    private RealmTimeTable sunday;
    private RealmTimeTable monday;
    private RealmTimeTable tuesday;
    private RealmTimeTable wednesday;
    private RealmTimeTable thursday;
    private RealmTimeTable friday;
    private RealmTimeTable saturday;

    public RealmTimeTable getSunday() {
        return sunday;
    }

    public void setSunday(RealmTimeTable sunday) {
        this.sunday = sunday;
    }

    public RealmTimeTable getMonday() {
        return monday;
    }

    public void setMonday(RealmTimeTable monday) {
        this.monday = monday;
    }

    public RealmTimeTable getTuesday() {
        return tuesday;
    }

    public void setTuesday(RealmTimeTable tuesday) {
        this.tuesday = tuesday;
    }

    public RealmTimeTable getWednesday() {
        return wednesday;
    }

    public void setWednesday(RealmTimeTable wednesday) {
        this.wednesday = wednesday;
    }

    public RealmTimeTable getThursday() {
        return thursday;
    }

    public void setThursday(RealmTimeTable thursday) {
        this.thursday = thursday;
    }

    public RealmTimeTable getFriday() {
        return friday;
    }

    public void setFriday(RealmTimeTable friday) {
        this.friday = friday;
    }

    public RealmTimeTable getSaturday() {
        return saturday;
    }

    public void setSaturday(RealmTimeTable saturday) {
        this.saturday = saturday;
    }
}
