package com.example.puzzlegam.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.puzzlegam.R;
import com.example.puzzlegam.views.DetailsStage;
import com.example.puzzlegam.views.MainActivity;
import com.example.puzzlegam.views.SplashActivity;

public class JobServiceNotification extends JobService {



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("TAG", "onStartJob: ");
        displayNotification();
        jobFinished(params,true);
        return false;
    }




    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG", "onStopJob: ");
        return true;
    }

    private void displayNotification() {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("101","My NOtification CH", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager =  getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent  = new Intent(getBaseContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(),"101");
        builder.setSmallIcon(R.drawable.ic_notification)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("لم تلعب منذ فترة هيا مجددا للعب مرة اخرى "))
                .setContentTitle("Puzzle Game")
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_notification,"Play Now",pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat compat = NotificationManagerCompat.from(getBaseContext());
        compat.notify(101,builder.build());
    }

}
