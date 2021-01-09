package com.example.provistudent;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Notifications {
    private AlarmManager alarmManager;
    public PendingIntent alarmIntent1;
    public PendingIntent alarmIntent2;
    private Bazadanych bazadanych;

    public Notifications(Bazadanych bazadanych, AlarmManager alarmManager, Context context){
        this.bazadanych = bazadanych;
        this.alarmManager = alarmManager;

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("tekstpow", "Opłać opłaty stałe!");
        intent.putExtra("titlepow", "Opłać wszystkie");
        context.sendBroadcast(intent);
        alarmIntent1 = PendingIntent.getBroadcast(context, 0, intent, 0);

        Intent intent2 = new Intent(context, NotificationReceiver.class);
        intent2.putExtra("tekstpow", "Wprowadź dane z dzisiejszego dnia!");
        intent2.putExtra("titlepow", "Pomiń");
        context.sendBroadcast(intent2);
        alarmIntent2 = PendingIntent.getBroadcast(context, 1, intent2, 0);
    }

    public void odlozpowiadomienie1(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long interwal = 20000;
//        long interwal = 3600000L;
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interwal, alarmIntent1);
    }

    public void odlozpowiadomienie2(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long interwal = 3600000L;
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interwal, alarmIntent2);
    }

    public void ustawNowePowiadomieniaWAlarmManager1(){
        // Ustawienie daty pierwszego powiadomienia
        //        long interwal = 2592000000L;
        long interwal = 60000;
        int dzienint;
        int miesiacint;
        int rokint;
        int godzinaint;
        int minutaint;
        String powiadomieniadzien = null;
        String powiadomieniadzienczas = null;
        Cursor cursor2;
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadzien = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                powiadomieniadzienczas = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
            }
        }
        cursor2.close();
        SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dzienstring = new SimpleDateFormat("dd");
        SimpleDateFormat miesiacstring = new SimpleDateFormat("MM");
        SimpleDateFormat rokstring = new SimpleDateFormat("yyyy");

        SimpleDateFormat czasbaza = new SimpleDateFormat("hhmm");
        SimpleDateFormat godzinastring = new SimpleDateFormat("hh");
        SimpleDateFormat minutastring = new SimpleDateFormat("mm");


        Date dziendata = null;
        Date miesiacdata = null;
        Date rokdata = null;
        Date godzinadata = null;
        Date minutadata = null;
        try {
            dziendata = datebaza.parse(powiadomieniadzien);
            miesiacdata = datebaza.parse(powiadomieniadzien);
            rokdata = datebaza.parse(powiadomieniadzien);
            godzinadata = czasbaza.parse(powiadomieniadzienczas);
            minutadata = czasbaza.parse(powiadomieniadzienczas);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dzien = dzienstring.format(dziendata);
        String miesiac = miesiacstring.format(miesiacdata);
        String rok = rokstring.format(rokdata);
        String godzina = godzinastring.format(godzinadata);
        String minuta = minutastring.format(minutadata);

        dzienint = Integer.parseInt(dzien);
        miesiacint = Integer.parseInt(miesiac);
        rokint = Integer.parseInt(rok);
        godzinaint = Integer.parseInt(godzina);
        minutaint = Integer.parseInt(minuta);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, rokint);
        calendar.set(Calendar.MONTH, miesiacint);
        calendar.set(Calendar.DAY_OF_MONTH, dzienint);
        calendar.set(Calendar.HOUR_OF_DAY, godzinaint);
        calendar.set(Calendar.MINUTE, minutaint);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeInMillis(System.currentTimeMillis());
//        Cursor cursor = bazadanych.odczytajtekst4();
//        if(cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                   interwal = cursor.getLong(cursor.getColumnIndex("interwal"));
//            }
//        }
//        cursor.close();
        // Ustaw w Alarm Manager powtarzające się powiadomienie co interwał
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interwal, alarmIntent1);

    }

    public void ustawNowePowiadomieniaWAlarmManager2(){
        // Ustawienie daty pierwszego powiadomienia
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        long interwal = 86400000L;
        long interwal = 90000;
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
