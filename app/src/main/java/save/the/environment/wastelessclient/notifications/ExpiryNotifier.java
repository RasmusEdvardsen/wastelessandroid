package save.the.environment.wastelessclient.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.activities.InventoryActivity;

public class ExpiryNotifier {
    public static void createAndNotify(Context ctx){
        Intent intent = new Intent(ctx, InventoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            if (notificationManager != null) notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(ctx, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Wasteless")
                .setContentText("One or more of your products are about to expire!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        if (notificationManager != null) notificationManager.notify(0, notification);
    }
}

/*@TargetApi(26)
    public void Notify(){
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The id of the channel.
        String id = "my_channel_01";

        // The user-visible name of the channel.
        CharSequence name = "W_CH_NM";

        // The user-visible description of the channel.
        String description = "Notifications about expiring foods";

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

        // Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);



        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;

        // The id of the channel.
        String CHANNEL_ID = "my_channel_01";

        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(MainActivity.this)
                .setContentTitle("Wasteless")
                .setContentText("One or more of your products are about to expire!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .build();

        // Issue the notification.
        mNotificationManager.notify(0, notification);


    }*/

    /*Intent intent = new Intent(this, InventoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(getBaseContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("qwr")
                .setContentText("qwewqr")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(0, notification);*/



    /*Intent intent = new Intent(ctx, InventoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > 26) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(ctx, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Wasteless")
                .setContentText("One or more of your products are about to expire")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(0, notification);*/