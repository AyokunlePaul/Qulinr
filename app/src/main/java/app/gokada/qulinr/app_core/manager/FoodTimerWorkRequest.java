package app.gokada.qulinr.app_core.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import androidx.work.Worker;
import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.screens.home.HomeActivity;

import static android.content.Context.VIBRATOR_SERVICE;

public class FoodTimerWorkRequest extends Worker{

    private String NOTIFICATION_CHANNEL_ID = "Channel Id";

    public FoodTimerWorkRequest(){
        Log.i("Worker", "No argument constructor.");
    }

    @NonNull
    @Override
    public Worker.WorkerResult doWork() {
        Log.i("Worker", "Work successfully done. ");
        Context context = QulinrApplication.getQulinrApplicationContext();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = createMediaNotification(manager);
        manager.notify(23, notification);
        playSoundAndVibrate();
        return Worker.WorkerResult.SUCCESS;
    }

    private Notification createMediaNotification(NotificationManager manager){
        Context context = QulinrApplication.getQulinrApplicationContext();
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        builder.setAutoCancel(true);
        builder.setContentTitle("Qulinr");
        builder.setContentText("Is Food ready?");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(PendingIntent.getActivity(context, 2938,
                new Intent(context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                PendingIntent.FLAG_CANCEL_CURRENT));
        builder.setOnlyAlertOnce(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "MATRIX_PLAYER_CHANNEL", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            assert manager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            manager.createNotificationChannel(notificationChannel);
        }

        return builder.build();
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
