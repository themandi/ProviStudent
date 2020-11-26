package com.example.provistudent;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewCheatdayActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    TextView polekwota;
    TextView polewydatek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cheatday);

        bazadanych = new Bazadanych(NewCheatdayActivity.this);
        cursor = bazadanych.odczytajtekst5();

        polewydatek = findViewById(R.id.polewydatek);
        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NewCheatdayActivity.this, CheatdayActivity.class);
                startActivity(intent);
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Integer usunwiersz = bazadanych.usuntekst5(polekwota.getText().toString());
                if (usunwiersz > 0)
                    Toast.makeText(getApplicationContext(), "Usunięto!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Nie można usunąć wiersza", Toast.LENGTH_SHORT).show();
            }
        });

        przyciskedytuj = (Button) findViewById(R.id.edytuj);
        przyciskedytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kwotapole = polekwota.getText().toString();
                int kwota = Integer.parseInt(polekwota.getText().toString());
                String wydatek = polewydatek.getText().toString();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Nie można zaaktualizować!", Toast.LENGTH_SHORT).show();
                } else if (cursor.getCount() > 0) {
                    if (!kwotapole.isEmpty()) {
                        if (bazadanych.zaaktualizujtekst5(wydatek, kwota)) {
                            polekwota.setText("");
                            Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Nie zostały wprowadzone dane!", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });

        przyciskwyswietl = (Button) findViewById(R.id.wyswietl);
        przyciskwyswietl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cursor = bazadanych.odczytajtekst5();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID: " + cursor.getString(0) + "\n");
                    buffer.append("Opłata: " + cursor.getString(1) + "\n");
                    buffer.append("Kwota: " + cursor.getString(2) + "\n");
                }
                wyswietlwiadomosc("Zapisane wydatki stałe: ", buffer.toString());
                cursor.close();
            }
        });

        przyciskdodaj = (Button) findViewById(R.id.dodaj);
        przyciskdodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onDodaj();
            }
        });

        przyciskzapisz = (Button) findViewById(R.id.zapisz);
        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onZapisz();
            }
        });
    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Błąd! Nic nie zostało zapisane", Toast.LENGTH_SHORT).show();
        } else if (cursor.getCount() > 0) {
            Toast.makeText(getApplicationContext(), "Zapisano!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewCheatdayActivity.this, CheatdayActivity.class);
            startActivity(intent);
        }
        cursor.close();
    }

    void onDodaj() {
        String kwotapole = polekwota.getText().toString();
        int kwota = Integer.parseInt(polekwota.getText().toString());
        String wydatek = polewydatek.getText().toString();


        if (!kwotapole.isEmpty()) {
            if (bazadanych.dodajtekst5(wydatek, kwota)) {
                polekwota.setText("");
                polewydatek.setText("");
            }
            Toast.makeText(getApplicationContext(), "Dodano!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Błąd! Nic nie zostało wprowadzone.", Toast.LENGTH_SHORT).show();
        }
    }

    public void wyswietlwiadomosc(String tytul, String wiadomosc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.show();
    }

}
