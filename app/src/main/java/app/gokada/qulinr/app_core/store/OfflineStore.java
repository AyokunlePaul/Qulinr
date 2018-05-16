package app.gokada.qulinr.app_core.store;

import android.content.Context;

import javax.inject.Inject;

public class OfflineStore {

    private Context context;

    @Inject
    public OfflineStore(Context context){
        this.context = context;
    }

}
