package app.gokada.qulinr.receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import app.gokada.qulinr.QulinrApplication;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        QulinrApplication application = QulinrApplication.get(context.getApplicationContext());
        Notification notification = application.createMediaNotification();
        application.getManager().notify(23, notification);

    }


}
