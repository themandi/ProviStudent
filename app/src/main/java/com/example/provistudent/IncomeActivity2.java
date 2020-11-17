package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IncomeActivity2 extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    EditText polekwota2;
    String zasob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income2);

        bazadanych = new Bazadanych(IncomeActivity2.this);
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

        polekwota2 = findViewById(R.id.polekwota2);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(IncomeActivity2.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Integer usunwiersz = bazadanych.usuntekst2(polekwota2.getText().toString());
                if(usunwiersz > 0)
                    Toast.makeText(getApplicationContext(), "Usunięto!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Nie można usunąć wiersza",Toast.LENGTH_SHORT).show();
            }
        });

        przyciskedytuj = (Button) findViewById(R.id.edytuj);
        przyciskedytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kwota = polekwota2.getText().toString();
                int numer = 1;
                zasob = "Karta płatnicza " + numer;
                numer++;
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Nie można zaaktualizować!",Toast.LENGTH_SHORT).show();
                }
                else if (cursor.getCount() > 0) {
                    if (bazadanych.zaaktualizujtekst2(zasob, kwota)) {
                        polekwota2.setText("");
                        Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!",Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });

        przyciskwyswietl = (Button) findViewById(R.id.wyswietl);
        przyciskwyswietl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cursor = bazadanych.odczytajtekst2();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID: " + cursor.getString(0) + "\n");
                    buffer.append("Zasób: " + cursor.getString(1) + "\n");
                    buffer.append("Kwota: " + cursor.getString(2) + "\n");
                    Toast.makeText(getApplicationContext(), "1: " + cursor.getString(1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "2: " + cursor.getString(2), Toast.LENGTH_SHORT).show();
                }
                wyswietlwiadomosc("Zapisane zasoby: ", buffer.toString());
                cursor.close();
            }
        });

        przyciskdodaj = (Button) findViewById(R.id.dodaj);
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
        if(cursor.getCount()==0) {
            Toast.makeText(getApplicationContext(), "Błąd! Nic nie zostało zapisane",Toast.LENGTH_SHORT).show();
        }
        else if(cursor.getCount()>0) {
            Toast.makeText(getApplicationContext(), "Zapisano!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(IncomeActivity2.this, RegisterActivity.class);
            startActivity(intent);
        }
        cursor.close();
    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onDodaj() {
        String kwota = polekwota2.getText().toString();
        int numer = 1;
        String zasob = "Karta płatnicza " + numer;
        numer++;
        if(!kwota.isEmpty()) {
            if (bazadanych.dodajtekst2(zasob, kwota)) {
                polekwota2.setText("");
            }
        }
        Toast.makeText(getApplicationContext(), "Dodano!",Toast.LENGTH_SHORT).show();
    }
    public void wyswietlwiadomosc(String tytul, String wiadomosc){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.show();
    }
}