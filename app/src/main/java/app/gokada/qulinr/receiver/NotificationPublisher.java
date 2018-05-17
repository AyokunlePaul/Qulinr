package app.gokada.qulinr.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.screens.home.HomeActivity;

import static android.content.Context.VIBRATOR_SERVICE;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        QulinrApplication.get(context.getApplicationContext()).createMediaNotification();
        //Notification notification = createMediaNotification(manager);
        //playSoundAndVibrate();

    }

    private void playSoundAndVibrate(){
        Context context = QulinrApplication.getQulinrApplicationContext();
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd("notification.mp3");
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            player.setVolume(1f,1f);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("PLAYER", e.getMessage());
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }


}
