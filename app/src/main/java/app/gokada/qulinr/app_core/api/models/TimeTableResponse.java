package app.gokada.qulinr.app_core.api.models;

import com.google.gson.annotations.SerializedName;

public class TimeTableResponse {
    @SerializedName("breakfast")
    private String breakfast;
    @SerializedName("lunch")
    private String lunch;
    @SerializedName("dinner")
    private String dinner;

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }
}
