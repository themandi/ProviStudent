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

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "Kanal";
    Bazadanych bazadanych;
    public static int currentId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Konfiguracja zależnosci
        bazadanych = new Bazadanych(context);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = currentId++;
        String tekstpow = intent.getExtras().getString("tekstpow");
        String titlepow = intent.getExtras().getString("titlepow");
        String contentintent = intent.getExtras().getString("intent");

        stworzkanalpowiadomien(notificationManager);

        PendingIntent activity = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        if(contentintent.equals("intent")) {
            activity = PendingIntent.getActivity(context, 0, new Intent(context, ConstantCashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if(contentintent.equals("intent2")) {
            activity = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent oplaclubpomin = new Intent(context, ActionReceiver.class);
        oplaclubpomin.putExtra("action", "OPLACPOMIN_ACTION");
        oplaclubpomin.putExtra("tekstpow",tekstpow);
        oplaclubpomin.putExtra("titlepow",titlepow);
        oplaclubpomin.putExtra("NotId", id);

        PendingIntent pendingIntentoplaclubpomin = PendingIntent.getBroadcast(context, 2, oplaclubpomin, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent odloz = new Intent(context, ActionReceiver.class);
        odloz.putExtra("action", "ODLOZ_ACTION");
        odloz.putExtra("tekstpow", tekstpow);
        odloz.putExtra("titlepow", titlepow);
        odloz.putExtra("NotId", id);
        PendingIntent pendingIntentodloz = PendingIntent.getBroadcast(context, 3, odloz, PendingIntent.FLAG_UPDATE_CURRENT);

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
                .setContentIntent(activity)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.MAGENTA, 3000, 3000)
                .setSound(uri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.mipmap.logo, titlepow, pendingIntentoplaclubpomin)
                .addAction(R.mipmap.logo, "Odłóż", pendingIntentodloz);
        notificationManager.notify(id, builder.build());
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