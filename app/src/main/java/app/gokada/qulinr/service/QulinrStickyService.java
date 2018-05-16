package app.gokada.qulinr.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.screens.home.HomeActivity;
import app.gokada.qulinr.screens.home.HomeActivityVM;

public class QulinrStickyService extends Service implements LifecycleOwner{

    private String NOTIFICATION_CHANNEL_ID = "Channel Id";

    @Inject
    HomeActivityVM viewModel;

    private LifecycleRegistry registry;

    private QulinrMainComponent component;

    private NotificationManager manager;

    private CountDownTimer timer;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("onCreate", " ========= HERE");

        registry = new LifecycleRegistry(this);
        registry.markState(Lifecycle.State.CREATED);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        component = QulinrApplication.get(this).getComponent();
        component.inject(this);
    }

    public QulinrStickyService(){}

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        registry.markState(Lifecycle.State.STARTED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("app.gokada.quilnr.RestartService");
        sendBroadcast(broadcastIntent);
        registry.markState(Lifecycle.State.DESTROYED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("onStartCommand", " ========= HERE");
        Log.i("onStartCommand", " ========= " + Boolean.toString(viewModel == null));
        if (timer != null){
            stopCounter();
        }
        if (viewModel.getCachedLong() != null && !(viewModel.getCachedLong() == 0)){
            startCounter(viewModel.getCachedLong());
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
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

    public void startCounter(Long value){
        timer = new CountDownTimer(value, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Breaks the flow
                viewModel.cacheCounter(millisUntilFinished);
                Log.i("COUNT DOWN", " ========== " + Long.toString(millisUntilFinished));
                Log.i("COUNT DOWN", " ========== " + Boolean.toString(viewModel == null));
            }

            @Override
            public void onFinish() {
                //Breaks the flow
                Notification notification = createMediaNotification(manager);
                manager.notify(23, notification);
                playSoundAndVibrate();

                viewModel.deleteCountedValues();
            }
        };
        timer.start();
    }

    public void stopCounter(){
        timer.cancel();
        timer = null;
    }
}
