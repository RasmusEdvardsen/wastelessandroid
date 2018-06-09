package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.scheduling.ExpiryScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanBtn = findViewById(R.id.scanBtn);
        Button inventoryBtn = findViewById(R.id.inventoryBtn);

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
}
