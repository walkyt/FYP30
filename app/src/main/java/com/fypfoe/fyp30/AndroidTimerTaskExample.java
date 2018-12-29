package com.fypfoe.fyp30;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Timer;
        import java.util.TimerTask;
        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.widget.Toast;

public class AndroidTimerTaskExample extends AppCompatActivity {
        private Context context;
        Timer timer;
        TimerTask timerTask;

        //we are going to use a handler to be able to run in our TimerTask
        final Handler handler = new Handler();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            context=this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_set_timer2);
        }

        @Override
        protected void onResume() {

            super.onResume();

            //onResume we start our timer so it can start when the app comes from the background
            startTimer();
        }

        //Set Time Cycle
        public void startTimer() {

            //set a new Timer
            timer = new Timer();

            //initialize the TimerTask's job
            initializeTimerTask();

            //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
            timer.schedule(timerTask, 5000, 3000); //
        }

        public void stoptimertask(View v) {

            //stop the timer, if it's not already null
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }

        public void initializeTimerTask() {

            timerTask = new TimerTask() {
                public void run() {

                    //use a handler to run a toast that shows the current timestamp
                    handler.post(new Runnable() {
                        public void run() {
                            //get the current timeStamp
                            /*Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                            final String strDate = simpleDateFormat.format(calendar.getTime());


                            //show the toast
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), "dafuq",1);
                            toast.show();*/
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(context, "fuck")
                                            .setSmallIcon(R.drawable.ic_launcher_background)
                                            .setContentTitle("Notifications Example")
                                            .setContentText("This is a test notification");

                            Intent notificationIntent = new Intent(context, AndroidTimerTaskExample.class);

                            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            builder.setContentIntent(contentIntent);
                            builder.setAutoCancel(true);
                            long[] pattern = {1000,1000,1000,1000,1000,1000};
                            builder.setVibrate(pattern);
                            builder.setStyle(new NotificationCompat.InboxStyle());
                            Notification notification = builder.build();
                            builder.setSound(Uri.parse("android.resource://"
                                    + context.getPackageName() + "/" + R.raw.iuh));
                            // Add as notification
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(1, builder.build());
                        }
                    });
                }
            };
        }


}


