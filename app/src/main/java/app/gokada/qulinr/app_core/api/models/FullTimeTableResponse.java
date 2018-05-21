package app.gokada.qulinr.app_core.api.models;

import com.google.gson.annotations.SerializedName;

public class FullTimeTableResponse {

    @SerializedName("sunday")
    private TimeTableResponse sunday;

    @SerializedName("monday")
    private TimeTableResponse monday;

    @SerializedName("tuesday")
    private TimeTableResponse tuesday;

    @SerializedName("wednesday")
    private TimeTableResponse wednesday;

    @SerializedName("thursday")
    private TimeTableResponse thursday;

    @SerializedName("friday")
    private TimeTableResponse friday;

    @SerializedName("saturday")
    private TimeTableResponse saturday;

    public TimeTableResponse getSunday() {
        return sunday;
    }

    public void setSunday(TimeTableResponse sunday) {
        this.sunday = sunday;
    }

    public TimeTableResponse getMonday() {
        return monday;
    }

    public void setMonday(TimeTableResponse monday) {
        this.monday = monday;
    }

    public TimeTableResponse getTuesday() {
        return tuesday;
    }

    public void setTuesday(TimeTableResponse tuesday) {
        this.tuesday = tuesday;
    }

    public TimeTableResponse getWednesday() {
        return wednesday;
    }

    public void setWednesday(TimeTableResponse wednesday) {
        this.wednesday = wednesday;
    }

    public TimeTableResponse getThursday() {
        return thursday;
    }

    public void setThursday(TimeTableResponse thursday) {
        this.thursday = thursday;
    }

    public TimeTableResponse getFriday() {
        return friday;
    }

    public void setFriday(TimeTableResponse friday) {
        this.friday = friday;
    }

    public TimeTableResponse getSaturday() {
        return saturday;
    }

    public void setSaturday(TimeTableResponse saturday) {
        this.saturday = saturday;
    }
}
