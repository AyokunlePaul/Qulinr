package app.gokada.qulinr.app_core.dagger.modules;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.store.OfflineStore;
import app.gokada.qulinr.app_core.store.OnlineStore;
import dagger.Module;
import dagger.Provides;

@Module
public class DataStoreModule {

    @Provides
    @MainScope
    public DataStore provideDataStore(OnlineStore onlineStore, OfflineStore offlineStore){
        return new DataStore(onlineStore, offlineStore);
    }

}
