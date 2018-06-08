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
    private Button ocrBtn;
    private Button inventoryBtn;
    private Button pocrBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button) findViewById(R.id.scanBtn);
        ocrBtn = (Button) findViewById(R.id.ocrBtn);
        inventoryBtn = (Button) findViewById(R.id.inventoryBtn);
        pocrBtn2 = (Button) findViewById(R.id.ocrBtn2);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
            }
        });

        ocrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OcrResultActivity.class));
                Log.i("Information", "hej du kom aldrig ind på OCR");
            }
        });
        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InventoryActivity.class));
            }
        });

        pocrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, OcrResultActivity.class));

            }
        });


    }


}
