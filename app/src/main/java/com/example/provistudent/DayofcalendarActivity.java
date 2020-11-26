package com.example.provistudent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class DayofcalendarActivity extends AppCompatActivity{
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskedytuj;
    Button przyciskcheat;
    Bazadanych bazadanych;
    Cursor cursor;
    TextView dochodaktualny;
    TextView kwotadowydania;
    TextView wydanokwota;
    int sumadochodu;
    int sumawydatkow;
    int sumadochodaktualny;
    int kwotawydaj;
    int kwotawydana;
    boolean klikniete=false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayofcalendar);
        bazadanych = new Bazadanych(DayofcalendarActivity.this);
        cursor = bazadanych.odczytajtekst5();

        Intent calendarz = getIntent();
        String date = calendarz.getStringExtra("data2");
        TextView tekstdata = (TextView) findViewById(R.id.jakidzien);
        tekstdata.setText(date);

        Calendar cal=Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        sumadochodu = bazadanych.sumadochodu();
        sumawydatkow = bazadanych.sumawydatkow();

        dochodaktualny = findViewById(R.id.dochodaktualny);
        sumadochodaktualny = sumadochodu - sumawydatkow;
        dochodaktualny.setText("Dochód aktualny: " + Integer.toString(sumadochodaktualny) + " zł");

        kwotadowydania = findViewById(R.id.kwotadowydania);
        kwotawydaj = sumadochodu/days;
        kwotadowydania.setText("Sugerowany wydatek na dziś: " + Integer.toString(kwotawydaj) + " zł");

        wydanokwota = findViewById(R.id.wydanokwota);
        if(klikniete == false) {
            kwotawydana = kwotawydaj;
            onDodaj();
        }
        else {
            kwotawydana = bazadanych.kwotawydana();
        }
        wydanokwota.setText("Kwota wydatków dzisiejszych: " + Integer.toString(kwotawydana) + " zł");


        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(DayofcalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        przyciskedytuj = (Button) findViewById(R.id.edytujwydatki);
        przyciskedytuj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                klikniete = true;
                Intent intent = new Intent(DayofcalendarActivity.this, EditCashActivity.class);
                startActivity(intent);
            }
        });

        przyciskcheat = (Button) findViewById(R.id.cheatday);
        przyciskcheat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(DayofcalendarActivity.this, CheatdayActivity.class);
                intent.putExtra("activity","first");
                startActivity(intent);
            }
        });

        przyciskzapisz = (Button) findViewById(R.id.zapisz);
        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                onZapisz();
            }
        });
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        Intent intent = new Intent(DayofcalendarActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do zapisywania
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Dodaj"
    void onDodaj() {
        String wydatek = "Wydatek";
        int kwota = kwotawydaj;
        bazadanych.dodajtekst5(wydatek, kwota);
        while(cursor.moveToNext()) {
            Toast.makeText(getApplicationContext(),cursor.getString(2),Toast.LENGTH_SHORT).show();
        }
    }
}