package save.the.environment.wastelessclient.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ScheduledJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        //Reverting back to using handler might be necessary.
        AsyncTaskService asyncTaskService = new AsyncTaskService();
        asyncTaskService.getProductsAndHandleExpiration(getBaseContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
