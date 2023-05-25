package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NotificationsActivity extends AppCompatActivity {
    private static final int NOTIFICATION_DELAY = 1000; // Delay in milliseconds

    private Handler handler;
    private Runnable notificationRunnable;


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification();
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.notification_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notes_nav:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings_nav:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notification_nav:
                        return true;
                }
                return false;
            }
        });
    }

    private void scheduleNotification() {
        handler = new Handler();
        notificationRunnable = new Runnable() {
            @Override
            public void run() {
                // Create an intent to be broadcasted
                Intent intent = new Intent(NotificationsActivity.this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        NotificationsActivity.this,
                        0,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
                );

                // Get the AlarmManager and schedule the notification
                AlarmManager alarmManager = (AlarmManager) getSystemService(MainActivity.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + NOTIFICATION_DELAY,
                            pendingIntent
                    );
                }
            }
        };

        handler.postDelayed(notificationRunnable, NOTIFICATION_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null && notificationRunnable != null) {
            handler.removeCallbacks(notificationRunnable);
        }
    }
}