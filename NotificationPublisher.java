package com.muwuprojects.mfish;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mrmu on 09/04/2017.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "mfish-notification-id";
    public static String NOTIFICATION = "mfish-notification";

    public void onReceive(Context context, Intent intent) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String storedPreference = preferences.getString("fish_name", "Mr Fish");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification myNotification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.unlocked)
                .setContentTitle("mFish")
                .setContentText(storedPreference + " has a new activity!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build();

        notificationManager.notify(0, myNotification);

        //Intent notificationIntent = new Intent(context, MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        //pendingIntent.cancel();

    }


}
