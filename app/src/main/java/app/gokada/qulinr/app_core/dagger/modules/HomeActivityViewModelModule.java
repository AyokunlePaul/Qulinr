package app.gokada.qulinr.app_core.dagger.modules;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.store.OnlineStore;
import app.gokada.qulinr.screens.home.HomeActivityVM;
import dagger.Module;
import dagger.Provides;

@Module(includes = {OnlineStoreModule.class})
public class HomeActivityViewModelModule {

    @Provides
    @MainScope
    public HomeActivityVM provideHomeActivityVM(DataStore dataStore){
        return new HomeActivityVM(dataStore);
    }

}
