package app.gokada.qulinr.app_core.api.models;

import com.google.gson.annotations.SerializedName;

public class CreateMenuRequest {

    @SerializedName("foodType")
    private String foodType;
    @SerializedName("foodMenu")
    private String foodMenu;
    @SerializedName("timeEstimate")
    private String timeEstimate;

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(String foodMenu) {
        this.foodMenu = foodMenu;
    }

    public String getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(String timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    @Override
    public String toString() {
        return "CreateMenuRequest{" +
                "foodType='" + foodType + '\'' +
                ", foodMenu='" + foodMenu + '\'' +
                ", timeEstimate='" + timeEstimate + '\'' +
                '}';
    }
}
