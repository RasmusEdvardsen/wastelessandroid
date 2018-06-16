package save.the.environment.wastelessclient.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import save.the.environment.wastelessclient.miscellaneous.Constants;
import save.the.environment.wastelessclient.services.HandlerService;
import save.the.environment.wastelessclient.services.ReaderService;
import com.google.zxing.Result;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    final String[] scanResults = new String[3];
    final  String[] barcodeResults = new String[1];
    public String[] parseResults = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //which one-- this----
        setContentView(save.the.environment.wastelessclient.R.layout.activity_barcode);
        scannerView = new ZXingScannerView(this);
        //----or this?
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M)
        {
            if(!checkPermission())
                requestPermission();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions( this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
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

    @Override
    public void handleResult(final Result result) {
        Log.d("QRCodeScanner", result.getText());
        final String barcode = result.getText();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    // Create URL
                    URL loginURL = new URL(Constants.baseURL + Constants.scrapePath + "/" + barcode);

                    // Create connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();

                    if(httpURLConnection.getResponseCode() == 200){

                        String wordOccurrences = ReaderService.resultToString(httpURLConnection.getInputStream());
                        if(wordOccurrences.startsWith("\"")){
                            wordOccurrences = wordOccurrences.substring(1, wordOccurrences.length());
                        }
                        if(wordOccurrences.endsWith("\"")){
                            wordOccurrences = wordOccurrences.substring(0, wordOccurrences.length()-1);
                        }
                        String[] words = wordOccurrences.split(", ");

                        scanResults[0]=words[0];
                        scanResults[1]=words[1];
                        scanResults[2]=words[2];
                        barcodeResults[0]=barcode;

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                HandleResultsLater(result);
                            }
                        });
                    }
                }catch (Exception e){
                    HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                }
            }
        });
    }

    public void HandleResultsLater(final Result result){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        HandlerService.setBuilder(this, scanResults, barcodeResults[0]);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}
