package com.example.provistudent;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActionReceiver extends BroadcastReceiver {
    Notifications powiadomienia;
    Bazadanych bazadanych;
    @Override
    public void onReceive(Context context, Intent intent) {
        bazadanych = new Bazadanych(context);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        powiadomienia = new Notifications(bazadanych, (AlarmManager)context.getSystemService(Context.ALARM_SERVICE), context.getApplicationContext());
        String action = intent.getStringExtra("action");
        String tekstpow = intent.getExtras().getString("tekstpow");
        String titlepow = intent.getExtras().getString("titlepow");
        int id = intent.getIntExtra("NotId", -1);
        if (action.equals("OPLACPOMIN_ACTION")) {
            if (titlepow.equals("Opłać wszystkie")) {
                int kwota = bazadanych.sumawydatkowstalych();
                if(kwota != 0) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
                    String data_aktualnastring =  data_aktualna.format(cal.getTime());
                    int data_aktualnaint = Integer.parseInt(data_aktualnastring);
                    String wydatek = "Stały";
                    String cheatday = "Nie";
                    bazadanych.dodajtekst5(data_aktualnaint, wydatek, kwota, cheatday);
                }
            }
            if (titlepow.equals("Pomiń")) {
                notificationManager.cancel(id);
            }
        }
        else if (action.equals("ODLOZ_ACTION")) {
            if(tekstpow.equals("Opłać opłaty stałe!")) {
                powiadomienia.odlozpowiadomienie1();
            }
            else if(tekstpow.equals("Wprowadź dane z dzisiejszego dnia!")){
                powiadomienia.odlozpowiadomienie2();
            }
        }
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
        notificationManager.cancel(id);
    }
}
