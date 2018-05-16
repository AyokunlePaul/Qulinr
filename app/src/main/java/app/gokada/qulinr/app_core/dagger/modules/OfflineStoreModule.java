package app.gokada.qulinr.app_core.dagger.modules;

import android.content.Context;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.OfflineStore;
import app.gokada.qulinr.app_core.store.localdatabase.RealmManager;
import app.gokada.qulinr.global.ModelMapper;
import dagger.Module;
import dagger.Provides;

@Module
public class OfflineStoreModule {

    @Provides
    @MainScope
    public OfflineStore provideOfflineStoreModule(RealmManager manager, Context context, ModelMapper mapper){
        return new OfflineStore(manager, context, mapper);
    }

}
