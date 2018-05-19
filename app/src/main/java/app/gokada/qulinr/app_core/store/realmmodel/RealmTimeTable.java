package app.gokada.qulinr.app_core.store.realmmodel;

import io.realm.RealmObject;

public class RealmTimeTable extends RealmObject {
    private String breakfast;
    private String lunch;
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
