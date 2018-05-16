package app.gokada.qulinr.app_core.store;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import rx.Observable;

public class DataStore {

    private OnlineStore onlineStore;
    private OfflineStore offlineStore;

    @Inject
    public DataStore(OnlineStore onlineStore, OfflineStore offlineStore){
        this.offlineStore = offlineStore;
        this.onlineStore = onlineStore;
    }

    public Observable<QulinrResponse> createMenu(CreateMenuRequest menuRequest){
        return onlineStore.createMenu(menuRequest);
    }

}
