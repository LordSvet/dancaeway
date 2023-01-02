package com.example.dancway.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * Application class
 */
public class ApplicationClass extends Application {
    public static final String CHANNEL_ID_1 = "CHANNEL_1";
    public static final String CHANNEL_ID_2 = "CHANNEL_2";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREV = "PREV";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    /**
     * Set up notification channels
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel1 = new NotificationChannel(CHANNEL_ID_1,
                    "Channel(1)", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel1.setDescription("Channel 1");
            NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_ID_2,
                    "Channel(2)", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel1.setDescription("Channel 2");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel1);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }
}
