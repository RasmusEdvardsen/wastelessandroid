package save.the.environment.wastelessclient.scheduling;

import android.content.Context;
import android.util.Log;

import org.joda.time.Instant;
import org.joda.time.Interval;

import save.the.environment.wastelessclient.data.Product;
import save.the.environment.wastelessclient.data.UserModel;
import save.the.environment.wastelessclient.notifications.ExpiryNotifier;
import save.the.environment.wastelessclient.services.AsyncTaskService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExpiryScheduler {
    //TODO: THIS CAN BE DONE BETTER
    public static void scheduleExpiryChecker(final Context ctx){
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                AsyncTaskService asyncTaskService = new AsyncTaskService();
                asyncTaskService.GetProducts();

                //TODO: Check for products expiring < 1 day
                /*boolean expiring = false;
                for (Product product : UserModel.getProducts()){
                    Instant instant = new Instant();
                    Instant newInstant = new Instant(product.expirationDate);
                    Log.i("information", "now: " + instant.toString() + " future: " + newInstant.toString());
                    //Interval interval = new Interval(new Instant(product.expirationDate), new Instant());
                }*/

                ExpiryNotifier.createAndNotify(ctx);
            }
        }, 0, 1, TimeUnit.HOURS);
    }
}
