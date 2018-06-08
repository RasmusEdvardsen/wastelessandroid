package com.example.edvardsen.wastelessclient.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.FridgeObjects;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.HandlerService;
import com.example.edvardsen.wastelessclient.services.ReaderService;
import com.google.zxing.Result;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


import me.dm7.barcodescanner.zxing.ZXingScannerView;


import static android.Manifest.permission.CAMERA;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    TextView barcode;
    final String[] scanResults = new String[3];
    public String[] getScanResults(){
        return scanResults;
    }
    final  String[] barcodeResults = new String[1];

   public String[] parseResults = new String[10];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_barcode);
        // barcode = (TextView) findViewById(R.id.barcode);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }



    private void requestPermission() {
        ActivityCompat.requestPermissions( this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public static void setVisibility(View view, Integer integer){
        view.setVisibility(integer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }
    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(BarcodeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    /* private void secondDialog(DialogInterface.OnClickListener newListener){
        new android.support.v7.app.AlertDialog.Builder(BarcodeActivity.this)
                .setMessage("choose between these:")
                .setItems(scanResults.indexOf(3), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
    } */


    @Override
    public void handleResult(final Result result) {

        Log.d("QRCodeScanner", result.getText());

          /*barcode.getText().toString(); */
        final String barcode = result.getText();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    // Create URL
                    URL loginURL = new URL(Constants.baseURL + Constants.scrapePath + "/" + barcode);

                    // Create connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                    if(httpURLConnection.getResponseCode() == 200){


                        //TODO: RENAME JSONService to ReaderService, PUT THIS IN TO THAT SERVICE.
                        //TODO: JSONArray ???

                        String wordOccurrences = ReaderService.ResulttoString(httpURLConnection.getInputStream());
                        if(wordOccurrences.startsWith("\"")){
                            wordOccurrences = wordOccurrences.substring(1, wordOccurrences.length());
                        }
                        if(wordOccurrences.endsWith("\"")){
                            wordOccurrences = wordOccurrences.substring(0, wordOccurrences.length()-1);
                        }
                        String[] words = wordOccurrences.split(", ");
                        Log.i("information", Arrays.toString(words));
                        scanResults[0]=words[0];
                        scanResults[1]=words[1];
                        scanResults[2]=words[2];
                        barcodeResults[0]=barcode;

                        Log.i("information", scanResults[0]+scanResults[1]+words[2] );
                        //DU Laver et kald på UI THREAD SOm SKAL GØERS på MAIN :/

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                HandleResultsLater(result);
                            }
                        });

                    }
                }catch (Exception e){
                    Log.e("information", e.toString());
                    HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                }
            }
        });



    }

    public void HandleResultsLater(final Result result){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        HandlerService.setBuilder(this, scanResults, barcodeResults[0]);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(getBaseContext(), OcrResultActivity.class);
            Log.i("information barcode", barcodeResults[0]);
            intent.putExtra("ean", barcodeResults[0]);
            startActivity(intent);
            }
        });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public void secondDialog(final Result result){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String barcode = result.getText();
        builder.setTitle("Choose an option");

        builder.setPositiveButton("Scan", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                scanResults[0] = parseResults[0];
                scanResults[1] = parseResults[1];
                scanResults[2] = parseResults[2];
                scannerView.stopCameraPreview();
                startActivity(new Intent(getBaseContext(), OcrResultActivity.class));
                //scannerView.resumeCameraPreview(BarcodeActivity.this);
            }
        });


        builder.setPositiveButton("Scan More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scanResults[0] = parseResults[0];
                scanResults[1] = parseResults[1];
                scanResults[2] = parseResults[2];

                scannerView.resumeCameraPreview(BarcodeActivity.this);

            }
        });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}
