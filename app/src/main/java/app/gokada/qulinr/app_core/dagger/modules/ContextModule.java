package app.gokada.qulinr.app_core.dagger.modules;

import android.content.Context;

import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    @MainScope
    public Context context(){
        return context;
    }

}
