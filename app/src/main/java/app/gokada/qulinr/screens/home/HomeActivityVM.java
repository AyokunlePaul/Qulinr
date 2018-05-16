package app.gokada.qulinr.screens.home;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.view.CoreVM;
import rx.Subscriber;

public class HomeActivityVM extends CoreVM {

    private DataStore dataStore;

    @Inject
    public HomeActivityVM(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public void createMenu(CreateMenuRequest menuRequest, final OnMenuCreatedCallback onMenuCreatedCallback){
        dataStore.createMenu(menuRequest).subscribe(new Subscriber<QulinrResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onMenuCreatedCallback.onMenuCreated(null, e);
            }

            @Override
            public void onNext(QulinrResponse qulinrResponse) {
                onMenuCreatedCallback.onMenuCreated(qulinrResponse, null);
            }
        });
    }

    public interface OnMenuCreatedCallback {
        void onMenuCreated(QulinrResponse response, Throwable throwable);
    }

}
