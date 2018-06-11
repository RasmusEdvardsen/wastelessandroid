package save.the.environment.wastelessclient.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.scheduling.ExpiryScheduler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanBtn = findViewById(R.id.scanBtn);
        Button inventoryBtn = findViewById(R.id.inventoryBtn);

        Notify();

        ExpiryScheduler.ScheduleExpiryChecker();

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
            }
        });
        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InventoryActivity.class));
            }
        });
    }




    @TargetApi(26)
    public void Notify(){
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
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setChannelId(CHANNEL_ID)
                .build();

        // Issue the notification.
        mNotificationManager.notify(0, notification);


    }
}
