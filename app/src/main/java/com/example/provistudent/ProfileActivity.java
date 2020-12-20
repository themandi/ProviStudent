package com.example.provistudent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        TextView dochodprofil;
        TextView oplatystale;
        TextView powiadomieniadzienprofil;
        TextView cojakiczas;
        TextView oszczednosciprofil;
        TextView powiadomieniagodzinaprofil;
        TextView powiadomieniadzienczas;
        int sumaoszczednosci;
        int sumawydatkowstalych;
        int sumadochodu;
        String powiadomieniadzien;
        String powiadomieniadzientime;
        String coilepowiad;
        String powiadomieniagodz;

        bazadanych = new Bazadanych(ProfileActivity.this);
        cursor = bazadanych.odczytajtekst();

        TextView imie = (TextView) findViewById(R.id.imie);
        while(cursor.moveToNext()) {
            String zapisaneimie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
            imie.setText(zapisaneimie);
        }

        dochodprofil = findViewById(R.id.dochodprofil);
        sumadochodu = bazadanych.sumadochodu();
        dochodprofil.setText(Integer.toString(sumadochodu) + " zł");

        oplatystale = findViewById(R.id.oplatystale);
        sumawydatkowstalych = bazadanych.sumawydatkowstalych();
        oplatystale.setText(Integer.toString(sumawydatkowstalych) + " zł");

        powiadomieniadzienprofil = findViewById(R.id.powiadomieniadzienprofil);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniadzien = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                if (powiadomieniadzien == null || powiadomieniadzien.equals("")) {
                    powiadomieniadzienprofil.setText("Brak");
                } else {
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
                    SimpleDateFormat timebaza = new SimpleDateFormat("hhmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("hh:mm");
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
            while (cursor2.moveToNext()) {
                coilepowiad = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                if (coilepowiad.equals("Wybierz")) {
                    cojakiczas.setText("Brak");
                } else {
                    cojakiczas.setText(coilepowiad);
                }
            }
        }
        cursor2.close();

        powiadomieniagodzinaprofil = findViewById(R.id.powiadomieniagodzinaprofil);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                powiadomieniagodz = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                Toast.makeText(getApplicationContext(), ":" + powiadomieniagodz + ":", Toast.LENGTH_SHORT).show();
                if (powiadomieniagodz == null || powiadomieniagodz.equals("")) {
                    powiadomieniagodzinaprofil.setText("Brak");
                } else {
                    SimpleDateFormat timebaza = new SimpleDateFormat("hhmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("hh:mm");
                    Date time = null;
                    try {
                        time = timebaza.parse(powiadomieniagodz);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeformat = timestring.format(time);
                    powiadomieniagodzinaprofil.setText(timeformat);
                    powiadomieniagodzinaprofil.setText("Brak");
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
