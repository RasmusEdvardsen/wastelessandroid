package com.example.edvardsen.wastelessclient.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.example.edvardsen.wastelessclient.R;

public class NotificationShelduler {

    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.title))
                .setContentText(context.getString(R.string.content))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.cast_ic_notification_play)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.cast_ic_notification_1)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, NotificationShelduler.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);*/
    }
}
