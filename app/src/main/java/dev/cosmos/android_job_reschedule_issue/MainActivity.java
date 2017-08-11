package dev.cosmos.android_job_reschedule_issue;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobRequest;

import static android.app.Notification.PRIORITY_MAX;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scheduleJob(20000); //SCHEDULE FIRST JOB TO RUN IN 20 SECONDS
            }
        }, 10000); //WAIT 10 SECONDS SINCE ACTIVITY START TO SCHEDULE FIRST JOB
    }


    private static void scheduleJob(long millis){
        new JobRequest.Builder("tag")
                .setExact(millis)
                .build()
                .schedule();
    }



    public static class ReminderJob extends Job{
        @NonNull
        @Override
        protected Result onRunJob(Params params) {

            //show notification
            createNotificationForReminderAndShow(getContext());

            //schedule next job in 1 minute
            scheduleJob(60000);

            return Result.SUCCESS;
        }
    }



    public static class ReminderJobCreator implements JobCreator {
        @Override
        public Job create(String tag) {
            return new ReminderJob();
        }
    }


    public static void createNotificationForReminderAndShow(Context context) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.btn_default)
                        .setContentTitle("notification title")
                        .setContentText("notification body")
                        .setPriority(PRIORITY_MAX);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, ActivityTopOpenFromNotification.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);


        //show notification
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }


}
