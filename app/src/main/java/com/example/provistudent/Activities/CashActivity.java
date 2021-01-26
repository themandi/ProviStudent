package com.example.provistudent.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.R;

public class CashActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    Spinner spinner3;
    Spinner spinnerzaplata;
    TextView polekwota;
    String wydatek;
    String zasob;
    int kwota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bazadanych = new Bazadanych(CashActivity.this);
        cursor = bazadanych.odczytajtekst3();

        spinner3 = findViewById(R.id.spinner3);
        spinnerzaplata = findViewById(R.id.spinnerzaplata);
        polekwota = findViewById(R.id.polekwota);

        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent i = getIntent();
                Bundle extras = i.getExtras();
                if(extras.containsKey("Register")) {
                    Intent intent = new Intent(CashActivity.this, RegisterActivity.class);
                    finish();
                    startActivity(intent);
                }
                if(extras.containsKey("Settings")) {
                    Intent intent = new Intent(CashActivity.this, SettingsActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Integer usunwiersz = bazadanych.usuntekst3(polekwota.getText().toString());
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
                String kwotapole = polekwota.getText().toString();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Nie można zaaktualizować!",Toast.LENGTH_SHORT).show();
                }
                else if (cursor.getCount() > 0) {
                    if (!kwotapole.isEmpty()) {
                        try
                        {
                            kwota = Integer.parseInt(polekwota.getText().toString());
                        }catch(Throwable t)
                        {
                            Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zaaktualizowane dane, prosimy spróbować ponownie!", Toast.LENGTH_SHORT).show();
                        }
                        if (bazadanych.zaaktualizujtekst3(wydatek, kwota, zasob)) {
                            polekwota.setText("");
                            Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Nie zostały wprowadzone dane!", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });

        przyciskwyswietl = (Button) findViewById(R.id.wyswietl);
        przyciskwyswietl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cursor = bazadanych.odczytajtekst3();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("Wydatek: " + cursor.getString(1) + "\n");
                    buffer.append("Kwota: " + cursor.getString(2) + "\n");
                    buffer.append("Zapłata za pomocą: " + cursor.getString(3) + "\n");
                    buffer.append("\n");
                }
                wyswietlwiadomosc("Zapisane wydatki stałe: ", buffer.toString());
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

        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w oplatach stalych
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.wybordochodu, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wydatek = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 0:
                        Toast.makeText(parent.getContext(), "Prosze wybrać jedną z opcji!", Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        Intent intent = new Intent(CashActivity.this, NewCashActivity.class);
                        startActivity(intent);
                        break;
                }
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
            }
        });

        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w oplatach stalych
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.srodki, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerzaplata.setAdapter(adapter4);
        spinnerzaplata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        zasob = "Gotówka";
                        break;
                    case 1:
                        zasob = "Gotówka";
                        break;
                    case 2:
                        zasob = "Karta płatnicza";
                        break;
                }
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
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
        if(cursor.getCount()==0) {
            Toast.makeText(getApplicationContext(), "Błąd! Nic nie zostało zapisane",Toast.LENGTH_SHORT).show();
        }
        else if(cursor.getCount()>0) {
            Toast.makeText(getApplicationContext(), "Zapisano!",Toast.LENGTH_SHORT).show();
            Intent i = getIntent();
            Bundle extras = i.getExtras();
            if(extras.containsKey("Register")) {
                Intent intent = new Intent(CashActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            if(extras.containsKey("Settings")) {
                Intent intent = new Intent(CashActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            }
        }
        cursor.close();
    }

    void onDodaj() {
        String kwotapole = polekwota.getText().toString();
        if(!kwotapole.isEmpty()) {
            try
            {
                kwota = Integer.parseInt(polekwota.getText().toString());
            }catch(Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zapisane dane, prosimy edytować bądź usunąć wydatek", Toast.LENGTH_SHORT).show();
            }
            if (bazadanych.dodajtekst3(wydatek, kwota, zasob)) {
                polekwota.setText("");
            }
            Toast.makeText(getApplicationContext(), "Dodano!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Błąd! Nic nie zostało wprowadzone.",Toast.LENGTH_SHORT).show();
        }
    }

    public void wyswietlwiadomosc(String tytul, String wiadomosc){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.show();
    }
}