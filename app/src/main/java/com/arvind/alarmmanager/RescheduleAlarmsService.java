package com.arvind.alarmmanager;

import static com.arvind.alarmmanager.MainActivity.ALARM_REQ_CODE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;


public class RescheduleAlarmsService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("ShortAlarm")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int pendingFlags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        intent = new Intent(getApplicationContext(), MyReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQ_CODE, intent, pendingFlags);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                10000,
                10000,
                alarmPendingIntent
        );
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
