package com.example.provistudent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "Kanal";
    private static final String OPLACPOMIN_ACTION = "com.example.provistudent.OPLACPOMIN";
    private static final String ODLOZ_ACTION = "com.example.provistudent.ODLOZ";
    Bazadanych bazadanych;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Konfiguracja zależnosci
        bazadanych = new Bazadanych(context);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String tekstpow = intent.getExtras().getString("tekstpow");
        String titlepow = intent.getExtras().getString("titlepow");

        stworzkanalpowiadomien(notificationManager);

        PendingIntent mainactivity = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent constantcashactivity = PendingIntent.getActivity(context, 0, new Intent(context, ConstantCashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent oplaclubpomin = new Intent();
        oplaclubpomin.setAction(OPLACPOMIN_ACTION);
        PendingIntent pendingIntentoplaclubpomin = PendingIntent.getBroadcast(context, 1, oplaclubpomin, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent odloz = new Intent();
        odloz.setAction(ODLOZ_ACTION);
        PendingIntent pendingIntentodloz = PendingIntent.getBroadcast(context, 1, odloz, PendingIntent.FLAG_UPDATE_CURRENT);

        String action = intent.getAction();
        if (OPLACPOMIN_ACTION.equals(action)) {
            if (titlepow.equals("Opłać wszystkie")) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
            if (titlepow.equals("Pomiń")) {
                context.startActivity(new Intent(context, ProfileActivity.class));
//                powiadomienia.usunp2
            }
        }
        else if (ODLOZ_ACTION.equals(action)) {
            if(tekstpow.equals("Opłać opłaty stałe!")) {
//                powiadomienia.odlozpowiadomienie1();
            }
            else if(tekstpow.equals("Wprowadź dane z dzisiejszego dnia!")){
//                powiadomienia.odlozpowiadomienie2();
            }
        }


        // Wysłanie powiadomienia
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.logo_round)
                .setContentTitle(tekstpow)
                .setContentText("Dotknij, aby wyświetlić aplikację.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setContentIntent(mainactivity)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.MAGENTA, 3000, 3000)
                .setSound(uri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.mipmap.logo, titlepow, pendingIntentoplaclubpomin)
                .addAction(R.mipmap.logo, "Odłóż", constantcashactivity);
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