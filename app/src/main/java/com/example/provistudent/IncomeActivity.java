package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IncomeActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Bazadanych bazadanych;
    Cursor cursor;
    EditText polekwota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        bazadanych = new Bazadanych(IncomeActivity.this);
        cursor = bazadanych.odczytajtekst2();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No data",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext())
            {
                Toast.makeText(getApplicationContext(), "1: "+cursor.getString(1),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "2: "+cursor.getString(2),Toast.LENGTH_SHORT).show();
            }
        }

        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(IncomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                while(cursor.moveToNext()) {
                    String zasob = cursor.getString(cursor.getColumnIndex("zasob"));
                    String kwota = cursor.getString(cursor.getColumnIndex("kwota"));
                    bazadanych.usuntekst2(zasob, kwota);
                }
                Toast.makeText(getApplicationContext(), "Usunięto!",Toast.LENGTH_SHORT).show();
            }
        });

        przyciskdodaj = (Button) findViewById(R.id.dodajkolejne);
        przyciskdodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                onDodaj();
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
        String kwota = polekwota.getText().toString();
        int numer = 1;
        String zasob = "Gotowka" + numer;
        numer++;
        if(!kwota.isEmpty()) {
            if(cursor.getCount()==0) {
                if (bazadanych.dodajtekst2(zasob, kwota)) {
                    polekwota.setText("");
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Użyj przycisku 'Dodaj kolejne'!",Toast.LENGTH_SHORT).show();
            }
//            else if(cursor.getCount()>0) {
//                if (bazadanych.zaaktualizujtekst2(zasob, kwota)) {
//                    polekwota.setText("");
//                }
//            }
        }
        Toast.makeText(getApplicationContext(), "Zapisano!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(IncomeActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    void onDodaj() {
        String kwota = polekwota.getText().toString();
        int numer = 2;
        String zasob = "Gotowka " + numer;
        numer++;
        if(!kwota.isEmpty()) {
                if (bazadanych.dodajtekst2(zasob, kwota)) {
                    polekwota.setText("");
                }
            }
        Toast.makeText(getApplicationContext(), "Dodano kolejny!",Toast.LENGTH_SHORT).show();
    }
}