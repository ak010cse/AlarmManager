package com.arvind.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int ALARM_REQ_CODE = 100;
    MediaPlayer mediaPlayer;
    final Handler handler =new Handler();
    AlarmManager alarmManager;
    PendingIntent alarmPendingIntent;

    @SuppressLint("ShortAlarm")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButtonSnooze = findViewById(R.id.btn_snooze);
        Button mButtonFullStop = findViewById(R.id.btn_stop);
        Button mButtonStart = findViewById(R.id.btn_start);

//        https://github.com/navczydev/SplashScreenAPISample/tree/foreground-service-restrictions
//        https://www.sanfoundry.com/java-android-program-cancel-alarm-intent/
//        https://github.com/learntodroid/SimpleAlarmClock

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int pendingFlags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        Intent intent = new Intent(MainActivity.this, MyReceiver.class);
        alarmPendingIntent = PendingIntent.getBroadcast(MainActivity.this, ALARM_REQ_CODE, intent, pendingFlags);

        /*registerReceiver(broadcastReceiver, new IntentFilter("INTERNET_LOST"));
        handler.postDelayed(new Runnable() {

            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void run() {
                //call function
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long triggerTime = System.currentTimeMillis() + (10 * 1000);

                Intent iBroadCast = new Intent(MainActivity.this, MyReceiver.class);

                int pendingFlags;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
                } else {
                    pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, ALARM_REQ_CODE, iBroadCast, pendingFlags);
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

                handler.postDelayed(this, 10000);
            }
        }, 10000);
        mButtonSnooze.setOnClickListener(view -> {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        });
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Log.e( "Arvind Request: ","Playing Call" );

        }
    };
*/

        mButtonStart.setOnClickListener(view -> {

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    10000,
                    alarmPendingIntent
            );
        });

        mButtonSnooze.setOnClickListener(view -> {

            String toastText = String.format("Alarm Snooze");
            Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();


            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    0,
                    10000,
                    alarmPendingIntent
            );
            Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
            getApplicationContext().stopService(intentService);
        });
        mButtonFullStop.setOnClickListener(view -> {
            String toastText = String.format("Alarm Stop");
            Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
            Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
            stopService(intentService);
            onDestroy();

        });

    }

}