package com.example.provistudent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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
    TextView przekroczono;
    TextView wydanocheatday;
    int sumadochodu;
    int sumawydatkow;
    int sumadochodaktualny;
    int kwotawydaj;
    int kwotawydana;
    int sumaprzekroczono;
    int sumacheatday;
    String data;
    int datadozapisuint;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayofcalendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bazadanych = new Bazadanych(DayofcalendarActivity.this);
        cursor = bazadanych.odczytajtekst5();

        Intent calendarz = getIntent();
        String dzienimiesiac = calendarz.getStringExtra("data2");
        data = calendarz.getStringExtra("data");
        String datadozapisu = calendarz.getStringExtra("datadozapisu");
        datadozapisuint = Integer.parseInt(datadozapisu);
        TextView tekstdata = (TextView) findViewById(R.id.jakidzien);
        tekstdata.setText(dzienimiesiac);

        Calendar cal=Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        sumadochodu = bazadanych.sumadochodu();
        sumawydatkow = bazadanych.sumawydatkowstalych();

        dochodaktualny = findViewById(R.id.dochodaktualny);
        sumadochodaktualny = sumadochodu - sumawydatkow;
        dochodaktualny.setText("Dochód aktualny: " + Integer.toString(sumadochodaktualny) + " zł");

        kwotadowydania = findViewById(R.id.kwotadowydania);
        kwotawydaj = sumadochodu/days;
        kwotadowydania.setText("Sugerowany wydatek na dziś: " + Integer.toString(kwotawydaj) + " zł");

        wydanokwota = findViewById(R.id.wydanokwota);
        kwotawydana = bazadanych.kwotawydana();
        wydanokwota.setText("Kwota wydatków dzisiejszych: " + Integer.toString(kwotawydana) + " zł");

        przekroczono = findViewById(R.id.przekroczono);
        sumaprzekroczono = kwotawydana - kwotawydaj;
        przekroczono.setText("Przekroczono kwote o: " + Integer.toString(sumaprzekroczono) + " zł");

        wydanocheatday = findViewById(R.id.wydanocheatday);
        sumacheatday = bazadanych.sumacheatday();
        wydanocheatday.setText("Wydano z cheatday: " + Integer.toString(sumacheatday) + " zł");

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
                Intent intent = new Intent(DayofcalendarActivity.this, EditCashActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("data2",dzienimiesiac);
                intent.putExtra("datadozapisu",datadozapisu);
                startActivity(intent);
            }
        });

        przyciskcheat = (Button) findViewById(R.id.cheatday);
        przyciskcheat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(DayofcalendarActivity.this, CheatdayActivity.class);
                intent.putExtra("Calendar", "DayofcalendarActivity");
                intent.putExtra("data",data);
                intent.putExtra("data2",dzienimiesiac);
                intent.putExtra("datadozapisu",datadozapisu);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        Intent intent = new Intent(DayofcalendarActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do zapisywania
    }
}