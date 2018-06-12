package save.the.environment.wastelessclient.scheduling;

import android.content.Context;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import save.the.environment.wastelessclient.data.Product;
import save.the.environment.wastelessclient.data.UserModel;
import save.the.environment.wastelessclient.notifications.ExpiryNotifier;
import save.the.environment.wastelessclient.services.AsyncTaskService;

import java.util.Date;
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
                boolean expiring = false;
                boolean isExpired = false;
                UserModel.getInstance();
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                for (Product product : UserModel.getProducts()){
                    if(product.expirationDate.equals("null")) continue;
                    if(product.expirationDate.contains("T")){
                        String formattedDate = product.expirationDate.replace("T", " ");
                        DateTime expDate = formatter.parseDateTime(formattedDate);
                        DateTime nowDate = new DateTime();
                        long diff = expDate.getMillis() - nowDate.getMillis();
                        if(diff < 0) isExpired = true;
                        if(diff < 86400000 * 2) expiring = true;
                    }
                }

                //depending on isexpired and expiring, send correct message
                ExpiryNotifier.createAndNotify(ctx);
            }
        }, 0, 1, TimeUnit.HOURS);
    }
}
