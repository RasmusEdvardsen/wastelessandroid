package save.the.environment.wastelessclient.activities;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.data.DateRecog;
import save.the.environment.wastelessclient.data.FridgeObjects;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.CAMERA;

//import android.Manifest;

public class OcrActivity extends Activity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    DateRecog dateRecog = new DateRecog();
    Date OCResult;
    String[] Parseresults;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionID:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ActivityCompat.checkSelfPermission(OcrActivity.this, CAMERA)
                          != PackageManager.PERMISSION_GRANTED)   {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    }  catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            break;

        }
    }
    public void sendResults(Date OCRDate, String Ean){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(save.the.environment.wastelessclient.R.layout.activity_ocr);



        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Log.w("OCRActivity", "Detector dependicies are not available");

        }else{
            cameraSource = new CameraSource.Builder(getApplicationContext(),textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try{
                       if (ActivityCompat.checkSelfPermission(OcrActivity.this, CAMERA)
                               != PackageManager.PERMISSION_GRANTED){
                           ActivityCompat.requestPermissions(OcrActivity.this,
                                   new String[] {CAMERA},RequestCameraPermissionID);
                           return;

                           /* requestPermissions(new String[]{CAMERA}, */
                       }                                                               
                        cameraSource.start(cameraView.getHolder());
                    }  catch(IOException e){
                         e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }



                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                   final SparseArray<TextBlock> items =detections.getDetectedItems();
                   if(items.size()!=0){
                       textView.post(new Runnable() {
                           @Override
                           public void run() {

                               StringBuilder stringBuilder = new StringBuilder();

                               for(int i = 0; i<items.size(); i++){
                                   TextBlock item = items.valueAt(i);
                                   stringBuilder.append(item.getValue());
                                   stringBuilder.append("\n");

                                   try {
                                       String test = dateRecog.determineDateFormat(item.getValue());
                                       SimpleDateFormat formatter = new SimpleDateFormat(test);
                                       Date date = formatter.parse(item.getValue());
                                       //String test = dateRecog.determineDateFormat(item.getValue());
                                       if(false){

                                          // Date returnExpDate = dateRecog.getDate(item.getValue(),test);
                                           Toast.makeText(getApplicationContext(), "Date found!", Toast.LENGTH_LONG).show();
                                           Thread.currentThread().interrupt();
                                       }


                                   } catch (Exception e) {
                                       e.printStackTrace();
                                   }
                               }
                               textView.setText(stringBuilder.toString());


                           }
                       }) ;
                   }
                }
            });
        }



    }
}
