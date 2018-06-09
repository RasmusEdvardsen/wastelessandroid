package com.example.edvardsen.wastelessclient.scheduling;

import android.util.Log;

import com.example.edvardsen.wastelessclient.services.AsyncTaskService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Epico-u-01 on 6/9/2018.
 */

public class ExpiryScheduler {
    public static void ScheduleExpiryChecker(){
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                AsyncTaskService asyncTaskService = new AsyncTaskService();
                asyncTaskService.GetProductsAndRender();
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}
