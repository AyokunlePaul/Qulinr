package app.gokada.qulinr.app_core.dagger.components;

import app.gokada.qulinr.app_core.dagger.modules.HomeActivityViewModelModule;
import app.gokada.qulinr.app_core.dagger.modules.NetworkModule;
import app.gokada.qulinr.app_core.dagger.modules.RealmModules;
import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.screens.home.HomeActivity;
import dagger.Component;

@Component(modules = {NetworkModule.class, HomeActivityViewModelModule.class, RealmModules.class})
@MainScope
public interface QulinrMainComponent {

    void inject(HomeActivity homeActivity);

}
