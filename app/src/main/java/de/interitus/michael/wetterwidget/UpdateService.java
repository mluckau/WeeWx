package de.interitus.michael.wetterwidget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Michael on 20.09.2015.
 */
public class UpdateService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /* Todo: JSON Parsen */

        Log.d("WeeWxUpdateService", "WeeWx Wetterdaten aktualisiert!");
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
