package app.gokada.qulinr.app_core.dagger.modules;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.OnlineStore;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class OnlineStoreModule {

    @Provides
    @MainScope
    public OnlineStore provideOnlineStore(Retrofit retrofit){
        return new OnlineStore(retrofit);
    }

}
