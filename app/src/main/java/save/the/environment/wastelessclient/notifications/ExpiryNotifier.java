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

/**
 * Created by Epico-u-01 on 6/11/2018.
 */

public class ExpiryNotifier {
    public static void createAndNotify(Context ctx){
        Intent intent = new Intent(ctx, InventoryActivity.class);
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
        notificationManager.notify(0, notification);
    }
}
