package de.interitus.michael.wetterwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Michael on 17.09.2015.
 */
public class WidgetProvider extends AppWidgetProvider {

    String widgetstringname;
    String widgetstringzeit;
    String widgetstringtemp;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        widgetstringname = intent.getStringExtra("WIDGETNAME");
        widgetstringzeit = intent.getStringExtra("WIDGETZEIT");
        widgetstringtemp = intent.getStringExtra("WIDGETTEMP");
        remoteViews.setTextViewText(R.id.widget_stationName, widgetstringname);
        remoteViews.setTextViewText(R.id.widget_stationZeit, widgetstringzeit);
        remoteViews.setTextViewText(R.id.widget_stationTemp, widgetstringtemp);
        updateWidgetNow(context, remoteViews);

        super.onReceive(context, intent);
    }

    public void updateWidgetNow (Context context, RemoteViews remoteViews){
        ComponentName widgetComponent = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetComponent, remoteViews);
    }


}
