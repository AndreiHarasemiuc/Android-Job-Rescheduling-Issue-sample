package dev.cosmos.android_job_reschedule_issue;

import android.app.Application;

import com.evernote.android.job.JobManager;

/**
 * Created by cosmos on 8/11/2017.
 */

public class SampleApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        JobManager.create(this).addJobCreator(new MainActivity.ReminderJobCreator());
    }
}
