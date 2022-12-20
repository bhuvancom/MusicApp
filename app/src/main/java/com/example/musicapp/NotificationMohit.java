package com.example.musicapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * Author  : Bhuvaneshvar
 * Project : MusicApp
 * Date    : 11:40 AM
 **/

public class NotificationMohit {
    static final String channelId = "Mohit player channel";

    public static void showNotification(Song s, Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Now Playing")
                .setContentText(s.name)
                .setSmallIcon(R.drawable.ic_baseline_play_circle_filled_24)
                .setSilent(true)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notificationBuilder.build());
    }

    public static void showPopup(Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Message from menu")
                .setContentText("This notification was invoked from menu.")
                .setSmallIcon(R.drawable.ic_baseline_play_circle_filled_24)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notificationBuilder.build());
    }

    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
