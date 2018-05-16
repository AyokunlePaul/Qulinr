package app.gokada.qulinr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import app.gokada.qulinr.app_core.dagger.components.DaggerQulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.modules.ContextModule;
import io.realm.Realm;

public class QulinrApplication extends Application {

    private QulinrMainComponent component;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        component = DaggerQulinrMainComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static QulinrApplication get(Activity activity){
        return (QulinrApplication) activity.getApplication();
    }

    public static Context getQulinrApplicationContext(){
        return context;
    }

    public QulinrMainComponent getComponent() {
        return component;
    }

    private void initializeRealm(){
        Realm.init(this);
    }

}
