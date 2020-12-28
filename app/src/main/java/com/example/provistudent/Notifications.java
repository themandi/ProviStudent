package com.example.provistudent;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.Calendar;

public class Notifications {
    Cursor cursor;

    private NotificationModel aktualnePowiadomienia;

    private Bazadanych bazadanych;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public Notifications(Bazadanych bazadanych, AlarmManager alarmManager, Context context){
        this.bazadanych = bazadanych;
        this.alarmManager = alarmManager;

//        aktualnePowiadomienia = bazadanych.odczytaj();

        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public boolean czyWlaczone(){
        return aktualnePowiadomienia.czyWlaczone;
    }

    public int pobierzInterwal(){
        return aktualnePowiadomienia.interwal;
    }

    public void wlacz(boolean wlacz){
        // Zatrzymaj Alarm Manager jeżeli ma ustawienia poprzednie
        if(aktualnePowiadomienia.czyWlaczone){
            usunPowiadomieniaZAlarmManager();
        }
        // Ustaw nowe
        aktualnePowiadomienia.czyWlaczone = wlacz;
//        bazadanych.zapisz(aktualnePowiadomienia);
        // Zapisz w Alarm Manager nowe ustawienia jeżeli włączono
        if(aktualnePowiadomienia.czyWlaczone){
            ustawNowePowiadomieniaWAlarmManager();
        }
    }

    public void ustaw(int interwal) throws Exception {
        boolean czyPrzedUstawieniemWlaczone = aktualnePowiadomienia.czyWlaczone;
        if(czyPrzedUstawieniemWlaczone){
            wlacz(false);
        }
        aktualnePowiadomienia.interwal = interwal;
//        bazadanych.zapisz(aktualnePowiadomienia);
        if(czyPrzedUstawieniemWlaczone){
            wlacz(true);
        }
    }

    public void ustawNowePowiadomieniaWAlarmManager(){

        // Ustawienie daty pierwszego powiadomienia
        // Przykładowo obecny czas
        Calendar calendar = Calendar.getInstance();
        int datapow = bazadanych.datapowiadomienia();
        int godzpow = bazadanych.godzpowiadomienia();
        calendar.setTimeInMillis(System.currentTimeMillis());

        System.out.println("Ustaw");

        // Ustaw w Alarm Manager powtarzające się powiadomienie co interwał
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), aktualnePowiadomienia.interwal, alarmIntent);

    }

    public void usunPowiadomieniaZAlarmManager(){
        // Ususń z Alarm Manager poprzednie ustawienia
        alarmManager.cancel(alarmIntent);
    }

}
