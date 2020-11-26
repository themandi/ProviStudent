package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditCashActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Bazadanych bazadanych;
    TextView polekwota;
    Cursor cursor;
    int kwota;
    String wydatek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cash);

        bazadanych = new Bazadanych(EditCashActivity.this);
        cursor = bazadanych.odczytajtekst5();

        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(EditCashActivity.this, DayofcalendarActivity.class);
                startActivity(intent);
            }
        });

        przyciskzapisz = (Button) findViewById(R.id.zapisz);
        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onZapisz();
            }
        });
    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        wydatek = "Wydatek";
        String kwotapole = polekwota.getText().toString();
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Nie można zaaktualizować!",Toast.LENGTH_SHORT).show();
        }
        else if (cursor.getCount() > 0) {
            if (!kwotapole.isEmpty()) {
                kwota = Integer.parseInt(polekwota.getText().toString());
                if (bazadanych.zaaktualizujtekst3(wydatek, kwota)) {
                    polekwota.setText("");
                    Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!", Toast.LENGTH_SHORT).show();
                    while(cursor.moveToNext()) {
                        Toast.makeText(getApplicationContext(),cursor.getString(2),Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Nie zostały wprowadzone dane!", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
    }
}