package app.gokada.qulinr.app_core.dagger.modules;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import app.gokada.qulinr.app_core.store.localdatabase.RealmManager;
import app.gokada.qulinr.global.ModelMapper;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModules {

    @MainScope
    @Provides
    public RealmManager provideRealmManager(Realm realm){
        return new RealmManager(realm);
    }

    @Provides
    @MainScope
    public ModelMapper provideModelMapper(){
        return new ModelMapper();
    }

    @MainScope
    @Provides
    public Realm provideRealm(){
        return Realm.getDefaultInstance();
    }

}
