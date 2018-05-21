package app.gokada.qulinr.app_core.api;

import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface QulinrService {

    @POST("api/v1/createmenu")
    Observable<QulinrResponse> createMenu(@Body CreateMenuRequest menuRequest);

    @POST("api/v1/notifyslack")
    Observable<QulinrResponse> notifySlack(@Body NotifySlackRequest slackRequest);

    @GET("api/v1/timetable/{day}")
    Observable<QulinrResponse<TimeTableResponse>> getTimetableForToday(@Path("day") String day);

    @GET("api/v1/timetable")
    Observable<QulinrResponse<FullTimeTableResponse>> getFullTimetable();
}
