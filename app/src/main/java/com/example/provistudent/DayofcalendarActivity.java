package com.example.provistudent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayofcalendar);

        Intent calendarz = getIntent();
        String date = calendarz.getStringExtra("data2");
        TextView tekstdata = (TextView) findViewById(R.id.jakidzien);
        tekstdata.setText(date);

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
                startActivity(intent);
            }
        });

        przyciskcheat = (Button) findViewById(R.id.cheatday);
        przyciskcheat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(DayofcalendarActivity.this, CheatdayActivity.class);
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
        Intent intent = new Intent(DayofcalendarActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do dodawania
    }
}