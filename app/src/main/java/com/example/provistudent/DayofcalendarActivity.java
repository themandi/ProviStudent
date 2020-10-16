package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DayofcalendarActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskedytuj;
    Button przyciskcheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayofcalendar);

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
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onDodaj() {
        Intent intent = new Intent(DayofcalendarActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do dodawania
    }
}