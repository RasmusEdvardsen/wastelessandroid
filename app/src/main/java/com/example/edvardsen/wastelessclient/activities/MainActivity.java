package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.services.NotificationScheduler;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanBtn;
        Button inventoryBtn;
        NotificationScheduler notificationScheduler;

        notificationScheduler = new NotificationScheduler();
        notificationScheduler.scheduleNotification(getBaseContext(), 1000);

        scanBtn = findViewById(R.id.scanBtn);
        inventoryBtn = findViewById(R.id.inventoryBtn);

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
}
