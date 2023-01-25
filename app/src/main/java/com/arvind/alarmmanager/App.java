package com.arvind.alarmmanager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class App extends Application implements LifecycleEventObserver {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event==Lifecycle.Event.ON_STOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(new Intent(this, AlarmService.class));

            } else {
                this.startService(new Intent(this, AlarmService.class));

            }
        }else if (event==Lifecycle.Event.ON_DESTROY){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(new Intent(this, AlarmService.class));

            } else {
                this.startService(new Intent(this, AlarmService.class));

            }
        }

    }
}
