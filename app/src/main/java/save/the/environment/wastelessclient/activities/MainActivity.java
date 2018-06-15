package save.the.environment.wastelessclient.activities;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.scheduling.ExpiryScheduler;
import save.the.environment.wastelessclient.services.ScheduledJobService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanBtn = findViewById(R.id.scanBtn);
        Button inventoryBtn = findViewById(R.id.inventoryBtn);

        //ExpiryScheduler.scheduleExpiryChecker(getBaseContext());


        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder =
                new JobInfo.Builder(1,
                    new ComponentName(getPackageName(),
                        ScheduledJobService.class.getName()))
                .setPeriodic(1000 * 60 * 60 * 8);
        if((jobScheduler != null ? jobScheduler.schedule(builder.build()) : 0) == JobScheduler.RESULT_FAILURE)
            Log.i("information", "jobscheduling failed");


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
