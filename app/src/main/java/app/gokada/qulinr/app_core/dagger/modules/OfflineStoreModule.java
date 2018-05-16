package app.gokada.qulinr.app_core.dagger.modules;

import android.content.Context;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.OfflineStore;
import dagger.Module;
import dagger.Provides;

@Module
public class OfflineStoreModule {

    @Provides
    @MainScope
    public OfflineStore provideOfflineStoreModule(Context context){
        return new OfflineStore(context);
    }

}
