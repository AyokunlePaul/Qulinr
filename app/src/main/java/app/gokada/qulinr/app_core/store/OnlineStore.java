package app.gokada.qulinr.app_core.store;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.QulinrService;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.NotifySlackRequest;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnlineStore {

    private QulinrService service;

    @Inject
    public OnlineStore(Retrofit retrofit){
        service = retrofit.create(QulinrService.class);
    }

    public Observable<QulinrResponse> createMenu(CreateMenuRequest menuRequest){
        return service.createMenu(menuRequest)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<QulinrResponse> notifySlack(NotifySlackRequest slackRequest){
        return service.notifySlack(slackRequest)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
