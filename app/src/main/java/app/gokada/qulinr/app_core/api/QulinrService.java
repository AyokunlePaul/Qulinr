package app.gokada.qulinr.app_core.api;

import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface QulinrService {

    @POST("api/v1/createmenu")
    Observable<QulinrResponse> createMenu(@Body CreateMenuRequest menuRequest);

    @POST("api/v1/notifyslack")
    Observable<QulinrResponse> notifySlack(@Body NotifySlackRequest slackRequest);
}
