package com.example.provistudent;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.Calendar;

public class Notifications {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent1;
    private PendingIntent alarmIntent2;
    private Bazadanych bazadanych;

    public Notifications(Bazadanych bazadanych, AlarmManager alarmManager, Context context){
        this.bazadanych = bazadanych;
        this.alarmManager = alarmManager;
        Cursor cursor = bazadanych.odczytajtekst4();
        if(cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               if(cursor.getString(cursor.getColumnIndex("czywlaczone")).equals("Yes")) {
//                   int interwal = cursor.getInt(cursor.getColumnIndex("interwal"));
                   ustawNowePowiadomieniaWAlarmManager1();
               }
                if(cursor.getString(cursor.getColumnIndex("czywlaczone2")).equals("Yes")) {
//                    int interwal = cursor.getInt(cursor.getColumnIndex("interwal"));
                    ustawNowePowiadomieniaWAlarmManager2();
                }
            }
        }
        cursor.close();

        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent1 = PendingIntent.getBroadcast(context, 0, intent, 0);

        Intent intent2 = new Intent(context, NotificationReceiver.class);
        alarmIntent2 = PendingIntent.getBroadcast(context, 1, intent2, 0);
    }
    public void ustawNowePowiadomieniaWAlarmManager1(){
        // Ustawienie daty pierwszego powiadomienia
        // Przykładowo obecny czas
        // Ustaw w Alarm Manager powtarzające się powiadomienie co interwał
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int interwal = 60000;
//        Cursor cursor = bazadanych.odczytajtekst4();
//        if(cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                if (cursor.getString(cursor.getColumnIndex("ID")).equals("1")) {
//                   int interwal = cursor.getInt(cursor.getColumnIndex("interwal"));
//                    ustawNowePowiadomieniaWAlarmManager1();
//                }
//            }
//        }
//        cursor.close();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interwal, alarmIntent1);

    }

    public void ustawNowePowiadomieniaWAlarmManager2(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int interwal = 60000;
        // Ustaw w Alarm Manager powtarzające się powiadomienie co interwał
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interwal, alarmIntent2);

    }

    public void usunPowiadomieniaZAlarmManager1(){
        // Usuń z Alarm Manager poprzednie ustawienia Powiadomiwnia ID 1
        alarmManager.cancel(alarmIntent1);
    }
    public void usunPowiadomieniaZAlarmManager2(){
        // Ususń z Alarm Manager poprzednie ustawienia Powiadomiwnia ID 2
        alarmManager.cancel(alarmIntent2);
    }
}
