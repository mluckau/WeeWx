package de.interitus.michael.wetterwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Michael on 20.09.2015.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){

            AlarmManager updateService = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

            Intent startUpdateServiceIntent = new Intent(context, UpdateService.class);
            PendingIntent startUpdateServicePendingIntent = PendingIntent.getService(context,0,startUpdateServiceIntent,0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis() + 1000*60*10);

            updateService.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 1000*60*10, startUpdateServicePendingIntent);

        }

    }
}
