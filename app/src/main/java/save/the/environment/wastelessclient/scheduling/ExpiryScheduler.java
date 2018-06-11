package save.the.environment.wastelessclient.scheduling;

import android.util.Log;

import save.the.environment.wastelessclient.services.AsyncTaskService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Epico-u-01 on 6/9/2018.
 */

public class ExpiryScheduler {
    //TODO: THIS CAN BE DONE BETTER
    public static void ScheduleExpiryChecker(){
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Reloads UserModel products
                AsyncTaskService asyncTaskService = new AsyncTaskService();
                asyncTaskService.GetProducts();

                //TODO: Check for products expiring < 1 day

                //TODO: If check above is true, notify
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}
