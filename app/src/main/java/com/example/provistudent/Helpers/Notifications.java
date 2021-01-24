package com.example.provistudent.Helpers;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.Receiver.NotificationReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Notifications {
    private AlarmManager alarmManager;
    public PendingIntent alarmIntent1;
    public PendingIntent alarmIntent2;
    public PendingIntent alarmIntent3;
    public PendingIntent alarmIntent4;
    private Bazadanych bazadanych;

    public Notifications(Bazadanych bazadanych, AlarmManager alarmManager, Context context){
        this.bazadanych = bazadanych;
        this.alarmManager = alarmManager;

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("tekstpow", "Opłać opłaty stałe!");
        intent.putExtra("titlepow", "Opłać wszystkie");
        intent.putExtra("intent", "intent");
        alarmIntent1 = PendingIntent.getBroadcast(context, 0, intent, 0);

        Intent intent2 = new Intent(context, NotificationReceiver.class);
        intent2.putExtra("tekstpow", "Wprowadź dane z dzisiejszego dnia!");
        intent2.putExtra("titlepow", "Pomiń");
        intent2.putExtra("intent", "intent2");
        alarmIntent2 = PendingIntent.getBroadcast(context, 1, intent2, 0);

        alarmIntent3 = PendingIntent.getBroadcast(context, 2, intent, 0);

        alarmIntent4 = PendingIntent.getBroadcast(context, 3, intent2, 0);
    }

    public void odlozpowiadomienie1(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //opóźnienie powiadomienia o 1h w ms
        long interwal = 3600000L;
        //ustawienie jednorazowego alarmu
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interwal, alarmIntent3);
    }

    public void odlozpowiadomienie2(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long interwal = 3600000L;
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interwal, alarmIntent4);
    }

    public void ustawNowePowiadomieniaWAlarmManager1(){
        // Ustawienie daty pierwszego powiadomienia
        //Ustawienie domyślnego interwalu na 30 dni
        long interwal = 2592000000L;
        String powiadomieniadzien = null;
        String powiadomieniadzienczas = null;
        //pobranie informacji z bazy danych
        Cursor cursor2;
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadzien = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                powiadomieniadzienczas = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
            }
        }
        cursor2.close();
        //Przeformatowanie daty za pomocą SimpleDateFormat na zrozumiałą dla komputera
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat czasFormat = new SimpleDateFormat("HHmm");
        czasFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date dziendata = null;
        Date godzinadata = null;
        try {
            dziendata = dataFormat.parse(powiadomieniadzien);
            godzinadata = czasFormat.parse(powiadomieniadzienczas);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date suma = new Date();
        suma.setTime(dziendata.getTime() + godzinadata.getTime());
        long datawms = suma.getTime();

        Cursor cursor = bazadanych.odczytajtekst4();
        if(cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                   interwal = cursor.getLong(cursor.getColumnIndex("interwal"));
            }
        }
        cursor.close();
        //Ustawienie cyklicznego alarmu co interwal
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, datawms, interwal, alarmIntent1);

    }

    public void ustawNowePowiadomieniaWAlarmManager2(){
        // Ustawienie daty pierwszego powiadomienia
        long interwal = 86400000L;
        String powiadomieniadane = null;
        Cursor cursor2;
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadane = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
            }
        }
        cursor2.close();

        SimpleDateFormat czasFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat godzFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minFormat = new SimpleDateFormat("mm");

        Date godzinadata = null;
        Date minutadata = null;
        try {
            godzinadata = czasFormat.parse(powiadomieniadane);
            minutadata = czasFormat.parse(powiadomieniadane);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String godzina = godzFormat.format(godzinadata);
        String minuta = minFormat.format(minutadata);

        int godzinaint = Integer.parseInt(godzina);
        int minutaint = Integer.parseInt(minuta);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, godzinaint);
        cal.set(Calendar.MINUTE, minutaint);
        cal.set(Calendar.SECOND, 0);
        Date suma = cal.getTime();
        long datawms = suma.getTime();
        // Ustaw w Alarm Manager powtarzające się powiadomienie co interwał
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, datawms, interwal, alarmIntent2);
    }

    public void usunPowiadomieniaZAlarmManager1(){
        // Usuń z Alarm Manager poprzednie ustawienia Powiadomienia ID 1
        alarmManager.cancel(alarmIntent1);
    }
    public void usunPowiadomieniaZAlarmManager2(){
        // Ususń z Alarm Manager poprzednie ustawienia Powiadomienia ID 2
        alarmManager.cancel(alarmIntent2);
    }
}
