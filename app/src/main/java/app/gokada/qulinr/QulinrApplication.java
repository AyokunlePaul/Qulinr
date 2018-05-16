package app.gokada.qulinr;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;

import app.gokada.qulinr.app_core.dagger.components.DaggerQulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.modules.ContextModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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

        initCalligraphy();

        initRealm();
    }

    public static QulinrApplication get(Activity activity){
        return (QulinrApplication) activity.getApplication();
    }

    public static QulinrApplication get(Service service){
        return (QulinrApplication) service.getApplication();
    }

    private void initCalligraphy(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/montserrat/Montserrat_Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void initRealm () {
        Realm.init (this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder ()
                .name ("QulinrRealm")
                .deleteRealmIfMigrationNeeded ()
                .schemaVersion (1)
                .build ();

        Realm.setDefaultConfiguration (realmConfiguration);
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
