package app.gokada.qulinr.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import app.gokada.qulinr.service.QulinrStickyService;

public class RestartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, QulinrStickyService.class));
    }

}
