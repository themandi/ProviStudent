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
        int sumaoszczednosci;
        int sumawydatkowstalych;
        int sumadochodu;
        String powiadomieniadzien;
        String coilepowiad;
        String powiadomieniagodz;

        bazadanych = new Bazadanych(ProfileActivity.this);
        cursor = bazadanych.odczytajtekst();
        cursor2 = bazadanych.odczytajtekst4();

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
        while(cursor2.moveToNext()) {
            powiadomieniadzien = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
            powiadomieniadzienprofil.setText(powiadomieniadzien);
        }

        cojakiczas = findViewById(R.id.cojakiczas);
        while(cursor2.moveToNext()) {
            coilepowiad = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
            cojakiczas.setText(coilepowiad);
        }

        powiadomieniagodzinaprofil = findViewById(R.id.powiadomieniagodzinaprofil);
        while(cursor2.moveToNext()) {
            powiadomieniagodz = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
            powiadomieniagodzinaprofil.setText(powiadomieniagodz);
        }

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
