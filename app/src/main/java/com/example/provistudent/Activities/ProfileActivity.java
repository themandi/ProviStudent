package com.example.provistudent.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button przyciskedytuj = findViewById(R.id.edytuj);
        Cursor cursor;
        Cursor cursor2;
        Bazadanych bazadanych;
        TextView przychodprofil;
        TextView oplatystale;
        TextView powiadomieniadzienprofil;
        TextView cojakiczas;
        TextView oszczednosciprofil;
        TextView powiadomieniagodzinaprofil;
        TextView powiadomieniadzienczas;
        TextView stanpowiadomien;
        TextView stanpowiadomien2;
        int sumaoszczednosci;
        int sumawydatkowstalych;
        int sumaprzychodu;
        String powiadomieniadzien;
        String czestotliwoscdzien;
        String powiadomieniadzientime;
        String coilepowiad;
        String powiadomieniagodz;
        String stanpowiadomienstring;
        String stanpowiadomienstring2;
        String powiadomieniatime;
        String powiadomieniadane;
        long interwal;
        String czywlaczone;
        String czywlaczone2;


        bazadanych = new Bazadanych(ProfileActivity.this);
        cursor = bazadanych.odczytajtekst();

        TextView imie = (TextView) findViewById(R.id.imie);
        while(cursor.moveToNext()) {
            String zapisaneimie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
            imie.setText(zapisaneimie);
        }

        przychodprofil = findViewById(R.id.przychodprofil);
        sumaprzychodu = bazadanych.sumaprzychodu();
        przychodprofil.setText(Integer.toString(sumaprzychodu) + " zł");

        oplatystale = findViewById(R.id.oplatystale);
        sumawydatkowstalych = bazadanych.sumawydatkowstalych();
        oplatystale.setText(Integer.toString(sumawydatkowstalych) + " zł");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        String datadozapisu =  data_aktualna.format(cal.getTime());
        int data_aktualnaint = Integer.parseInt(datadozapisu);

        powiadomieniadzienprofil = findViewById(R.id.powiadomieniadzienprofil);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadzien = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                powiadomieniatime = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                czestotliwoscdzien = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                powiadomieniadane = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                czywlaczone = cursor2.getString(cursor2.getColumnIndex("czywlaczone"));
                interwal = cursor2.getInt(cursor2.getColumnIndex("interwal"));
                czywlaczone2 = cursor2.getString(cursor2.getColumnIndex("czywlaczone2"));

                int powiadomieniadzienint = Integer.parseInt(powiadomieniadzien);
                if (powiadomieniadzien == null || powiadomieniadzien.equals("")) {
                    powiadomieniadzienprofil.setText("Brak");
                } else {
                    if(powiadomieniadzienint >= data_aktualnaint) {
                        SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = null;
                        try {
                            data = datebaza.parse(powiadomieniadzien);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String dataformat = datestring.format(data);
                        powiadomieniadzienprofil.setText(dataformat);
                    }
                    else if (powiadomieniadzienint < data_aktualnaint) {
                        if(czestotliwoscdzien.equals("Co miesiąc")) {
                            SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = null;
                            try {
                                data = datebaza.parse(powiadomieniadzien);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            cal.setTime(data);
                            cal.add(Calendar.MONTH, 1);

                            String databezformat = datebaza.format(cal.getTime());
                            String dataformat = datestring.format(cal.getTime());
                            powiadomieniadzienprofil.setText(dataformat);
                            bazadanych.zaaktualizujtekst4(databezformat, powiadomieniatime, czestotliwoscdzien, powiadomieniadane, czywlaczone, interwal, czywlaczone2);
                        }
                        if(czestotliwoscdzien.equals("Co trzy miesiące")) {
                            SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = null;
                            try {
                                data = datebaza.parse(powiadomieniadzien);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            cal.setTime(data);
                            cal.add(Calendar.MONTH, 3);
                            String databezformat = datebaza.format(cal.getTime());
                            String dataformat = datestring.format(cal.getTime());
                            powiadomieniadzienprofil.setText(dataformat);
                            bazadanych.zaaktualizujtekst4(databezformat, powiadomieniatime, czestotliwoscdzien, powiadomieniadane, czywlaczone, interwal, czywlaczone2);
                        }
                        if(czestotliwoscdzien.equals("Co pół roku")) {
                            SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = null;
                            try {
                                data = datebaza.parse(powiadomieniadzien);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            cal.setTime(data);
                            cal.add(Calendar.MONTH, 6);
                            String databezformat = datebaza.format(cal.getTime());
                            String dataformat = datestring.format(cal.getTime());
                            powiadomieniadzienprofil.setText(dataformat);
                            bazadanych.zaaktualizujtekst4(databezformat, powiadomieniatime, czestotliwoscdzien, powiadomieniadane, czywlaczone, interwal, czywlaczone2);
                        }
                        if(czestotliwoscdzien.equals("Co rok")) {
                            SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = null;
                            try {
                                data = datebaza.parse(powiadomieniadzien);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            cal.setTime(data);
                            cal.add(Calendar.MONTH, 12);
                            String databezformat = datebaza.format(cal.getTime());
                            String dataformat = datestring.format(cal.getTime());
                            powiadomieniadzienprofil.setText(dataformat);
                            bazadanych.zaaktualizujtekst4(databezformat, powiadomieniatime, czestotliwoscdzien, powiadomieniadane, czywlaczone, interwal, czywlaczone2);
                        }
                    }
                }
            }
        }
        cursor2.close();

        stanpowiadomien = findViewById(R.id.stanpowiadomien);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                stanpowiadomienstring = cursor2.getString(cursor2.getColumnIndex("czywlaczone"));
                if (stanpowiadomienstring == null || stanpowiadomienstring.equals("")) {
                    stanpowiadomien.setText("Stan powiadomienia: Wyłączone");
                } else {
                    if (stanpowiadomienstring.equals("Yes")) {
                        stanpowiadomien.setText("Stan powiadomienia: Włączone");
                    } else if (stanpowiadomienstring.equals("No")) {
                        stanpowiadomien.setText("Stan powiadomienia: Wyłączone");
                    }
                }
            }
        }
        cursor2.close();

        stanpowiadomien2 = findViewById(R.id.stanpowiadomien2);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                stanpowiadomienstring2 = cursor2.getString(cursor2.getColumnIndex("czywlaczone2"));
                if (stanpowiadomienstring2 == null || stanpowiadomienstring2.equals("")) {
                    stanpowiadomien2.setText("Stan powiadomienia: Wyłączone");
                } else {
                    if (stanpowiadomienstring2.equals("Yes")) {
                        stanpowiadomien2.setText("Stan powiadomienia: Włączone");
                    } else if (stanpowiadomienstring2.equals("No")) {
                        stanpowiadomien2.setText("Stan powiadomienia: Wyłączone");
                    }
                }
            }
        }
        cursor2.close();

        powiadomieniadzienczas = findViewById(R.id.powiadomieniadzienczas);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadzientime = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                if (powiadomieniadzientime == null || powiadomieniadzientime.equals("")) {
                    powiadomieniadzienczas.setText("Brak");
                } else {
                    SimpleDateFormat timebaza = new SimpleDateFormat("HHmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("HH:mm");
                    Date time = null;
                    try {
                        time = timebaza.parse(powiadomieniadzientime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeformat = timestring.format(time);
                    powiadomieniadzienczas.setText(timeformat);
                }
            }
        }
        cursor2.close();

        cojakiczas = findViewById(R.id.cojakiczas);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            coilepowiad = "Brak";
            while (cursor2.moveToNext()) {
                    coilepowiad = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                }
                    cojakiczas.setText(coilepowiad);
            }
        cursor2.close();

        powiadomieniagodzinaprofil = findViewById(R.id.powiadomieniagodzinaprofil);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniagodz = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                if (powiadomieniagodz == null || powiadomieniagodz.equals("")) {
                    powiadomieniagodzinaprofil.setText("Brak");
                } else {
                    SimpleDateFormat timebaza = new SimpleDateFormat("HHmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("HH:mm");
                    Date time = null;
                    try {
                        time = timebaza.parse(powiadomieniagodz);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeformat = timestring.format(time);
                    powiadomieniagodzinaprofil.setText(timeformat);
                }
            }
        }
        cursor2.close();

        oszczednosciprofil = findViewById(R.id.oszczednosciprofil);
        sumaoszczednosci = bazadanych.sumaoszczednosci();
        oszczednosciprofil.setText(Integer.toString(sumaoszczednosci) + " zł");

        przyciskedytuj.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            }
        });
        cursor.close();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
