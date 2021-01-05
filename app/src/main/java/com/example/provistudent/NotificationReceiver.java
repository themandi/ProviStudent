package com.example.provistudent;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "Kanal";
    Bazadanych bazadanych;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Konfiguracja zależnosci
        bazadanych = new Bazadanych(context);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Patrz niżej metodę
        stworzkanalpowiadomien(notificationManager);

        // Wysłanie powiadomienia
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.logo_round)
                .setContentTitle("Zapłać za opłaty stałe!")
                .setContentText("Bla")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.GREEN)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.logo, "Opłać wszystkie", null)
                .addAction(R.mipmap.logo, "Odłóż", null);
        notificationManager.notify(0, builder.build());
    }

    private void stworzkanalpowiadomien(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kanał";
            String description = "Powiadomienia";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }
}