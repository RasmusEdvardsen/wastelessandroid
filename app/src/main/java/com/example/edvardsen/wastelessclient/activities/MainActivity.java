package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.edvardsen.wastelessclient.R;



public class MainActivity extends Activity {

    private Button scanBtn;
    private Button inventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button) findViewById(R.id.scanBtn);
        inventoryBtn = (Button) findViewById(R.id.inventoryBtn);

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
