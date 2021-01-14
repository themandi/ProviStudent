package com.example.provistudent.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.R;

import java.util.Calendar;

public class DayofcalendarActivity extends AppCompatActivity{
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskedytuj;
    Button przyciskcheat;
    Bazadanych bazadanych;
    Cursor cursor;
    TextView przychod;
    TextView dochodaktualny;
    TextView kwotadowydania;
    TextView wydanokwota;
    TextView przekroczono;
    TextView wydanocheatday;
    int sumaprzychodu;
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
        try
        {
            datadozapisuint = Integer.parseInt(datadozapisu);
        }catch(Throwable t)
        {
            Toast.makeText(getApplicationContext(), "Error: Błąd zapisu daty z kalendarza!", Toast.LENGTH_SHORT).show();
        }
        TextView tekstdata = (TextView) findViewById(R.id.jakidzien);
        tekstdata.setText(dzienimiesiac);

        Calendar cal=Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        sumaprzychodu = bazadanych.sumaprzychodu();
        sumawydatkow = bazadanych.sumawydatkow();

        przychod = findViewById(R.id.przychod);
        findViewById(R.id.przychod).invalidate();
        przychod.setText("Przychód: " + Integer.toString(sumaprzychodu) + " zł");

        dochodaktualny = findViewById(R.id.dochodaktualny);
        findViewById(R.id.dochodaktualny).invalidate();
        sumadochodaktualny = sumaprzychodu - sumawydatkow;
        dochodaktualny.setText("Dochód: " + Integer.toString(sumadochodaktualny) + " zł");

        kwotadowydania = findViewById(R.id.kwotadowydania);
        findViewById(R.id.kwotadowydania).invalidate();
        kwotawydaj = sumaprzychodu/days;
        kwotadowydania.setText("Sugerowany wydatek na dziś: " + Integer.toString(kwotawydaj) + " zł");

        wydanokwota = findViewById(R.id.wydanokwota);
        findViewById(R.id.wydanokwota).invalidate();

        cursor = bazadanych.odczytajtekst5();
        int kwotawydanadzis = 0;
        if(cursor.getCount() == 0) {
            wydanokwota.setText("Brak wydatków dzisiejszych");
        }
        else {
            while(cursor.moveToNext()) {
                String databaza = cursor.getString(cursor.getColumnIndex("data"));
                if(databaza.equals(datadozapisu)) {
                    int data = cursor.getInt(cursor.getColumnIndex("data"));
                    kwotawydanadzis = bazadanych.sumawydatkowdzisiejszych(data);
                }
                wydanokwota.setText("Kwota wydatków dzisiejszych: " + Integer.toString(kwotawydanadzis) + " zł");
            }
        }
        cursor.close();

        wydanocheatday = findViewById(R.id.wydanocheatday);
        findViewById(R.id.wydanocheatday).invalidate();
        cursor = bazadanych.odczytajtekst5();
        int sumacheatdaydzis = 0;
        if(cursor.getCount() == 0) {
            wydanocheatday.setText("Brak wykorzystanych wydatków z cheatday");
        }
        else {
            while(cursor.moveToNext()) {
                String databaza = cursor.getString(cursor.getColumnIndex("data"));
                if(databaza.equals(datadozapisu)) {
                    int data = cursor.getInt(cursor.getColumnIndex("data"));
                    sumacheatdaydzis = bazadanych.sumacheatdaydzisiaj(data);
                }
                wydanocheatday.setText("Wydano dziś z cheatday: " + Integer.toString(sumacheatdaydzis) + " zł");
            }
        }
        cursor.close();

        przekroczono = findViewById(R.id.przekroczono);
        findViewById(R.id.przekroczono).invalidate();
        sumaprzekroczono = kwotawydanadzis + sumacheatdaydzis - kwotawydaj;
        if(sumaprzekroczono > 0) {
            przekroczono.setText("Przekroczono kwote o: " + Integer.toString(sumaprzekroczono) + " zł");
        }
        else {
            sumaprzekroczono = sumaprzekroczono * (-1);
            przekroczono.setText("Zaoszczędzono dzisiaj: " + Integer.toString(sumaprzekroczono) + " zł");
        }

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