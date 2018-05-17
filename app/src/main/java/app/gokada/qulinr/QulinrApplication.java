package app.gokada.qulinr;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.net.URI;

import app.gokada.qulinr.app_core.dagger.components.DaggerQulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.dagger.modules.ContextModule;
import app.gokada.qulinr.screens.home.HomeActivity;
import app.gokada.qulinr.screens.home.HomeActivityVM;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class QulinrApplication extends Application {

    private QulinrMainComponent component;
    private static Context context;

    public HomeActivityVM viewModel;

    private NotificationManager manager;

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        component = DaggerQulinrMainComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        initCalligraphy();
        initRealm();
    }

    public static QulinrApplication get(Activity activity){
        return (QulinrApplication) activity.getApplication();
    }

    public static QulinrApplication get(Service service){
        return (QulinrApplication) service.getApplication();
    }

    public static QulinrApplication get(Context context){
        return (QulinrApplication) context.getApplicationContext();
    }

    private void initCalligraphy(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/montserrat/Montserrat_Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public Notification createMediaNotification(){
        Context context = QulinrApplication.getQulinrApplicationContext();
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, NOTIFICATION_ID);

        builder.setAutoCancel(true);
        builder.setContentTitle("Qulinr");
        builder.setContentText("Is Food ready?");
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        Uri uri = Uri.parse("android.resource://app.gokada.qulinr/raw/notification");
        builder.setSound(uri);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(PendingIntent.getActivity(context, 2938,
                new Intent(context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                PendingIntent.FLAG_CANCEL_CURRENT));
        builder.setOnlyAlertOnce(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_ID, "MATRIX_PLAYER_CHANNEL", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            assert manager != null;
            builder.setChannelId(NOTIFICATION_ID);
            manager.createNotificationChannel(notificationChannel);
        }

        return builder.build();
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

    public NotificationManager getManager() {
        return manager;
    }
}
