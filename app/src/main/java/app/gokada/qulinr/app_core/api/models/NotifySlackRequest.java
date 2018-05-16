package app.gokada.qulinr.app_core.api.models;

import com.google.gson.annotations.SerializedName;

public class NotifySlackRequest {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
