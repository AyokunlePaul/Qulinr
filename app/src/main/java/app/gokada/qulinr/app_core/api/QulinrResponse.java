package app.gokada.qulinr.app_core.api;

import com.google.gson.annotations.SerializedName;

public class QulinrResponse<T> {

    @SerializedName(value = "data", alternate = {})
    private T data;
    @SerializedName("message")
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "QulinrResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
