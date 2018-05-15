package app.gokada.qulinr.app_core.api;

import com.google.gson.annotations.SerializedName;

public class QulinrResponse<T> {

    @SerializedName(value = "data", alternate = {})
    private T data;

}
