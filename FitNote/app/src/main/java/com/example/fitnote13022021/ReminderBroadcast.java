package com.example.fitnote13022021;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("RECEIVED", "Notification received!" + " UserName" + intent.getStringExtra("activeUserName"));

        // Get id & message from intent.
        String message = intent.getStringExtra("message");
        String activeUserName = intent.getStringExtra("activeUserName");

        // Call ProgramUserActivity when notification is tapped.
        Intent programUserAcIntent = new Intent(context, ProgramUserActivity.class);

        //we need to add this userName in order to take the user to his
        //ProgramUserActivity screen
        programUserAcIntent.putExtra("activeUserName", activeUserName);

        //Intent to go to user's exercise list when clicking on notification
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 0, programUserAcIntent, 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Exercise Time!")
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //According to the official documentation
        //starting in Android 8.0(API level 26)
        //all notifications must be assigned to a channel

        //the notificationID is a unique int for each notification that must be defined
        notificationManager.notify(NotificationID.getID(), builder.build());

    }

    public static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(0);
        public static int getID() {
            return c.incrementAndGet();
        }
    }

}


