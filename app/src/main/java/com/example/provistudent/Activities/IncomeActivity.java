package com.example.provistudent.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.R;

public class IncomeActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    EditText polekwota;
    String zasob;
    int kwota;
    EditText polegotowka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bazadanych = new Bazadanych(IncomeActivity.this);
        cursor = bazadanych.odczytajtekst2();

        polegotowka = findViewById(R.id.gotowka);
        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent i = getIntent();
                Bundle extras = i.getExtras();
                if(extras.containsKey("Register")) {
                    Intent intent = new Intent(IncomeActivity.this, RegisterActivity.class);
                    finish();
                    startActivity(intent);
                }
                if(extras.containsKey("Settings")) {
                    Intent intent = new Intent(IncomeActivity.this, SettingsActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Integer usunwiersz = bazadanych.usuntekst2(polekwota.getText().toString());
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
                String gotowkapole = polegotowka.getText().toString();

                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Nie można zaaktualizować!",Toast.LENGTH_SHORT).show();
                    }
                else if (cursor.getCount() > 0) {
                    if(!kwotapole.isEmpty()) {
                        try
                        {
                            kwota = Integer.parseInt(polekwota.getText().toString());
                        }catch(Throwable t)
                        {
                            Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zaaktualizowane dane, prosimy spróbować ponownie!", Toast.LENGTH_SHORT).show();
                        }
                        if (bazadanych.zaaktualizujtekst2(zasob, kwota, gotowkapole)) {
                            polekwota.setText("");
                            polegotowka.setText("");
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
                cursor = bazadanych.odczytajtekst2();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    return;
                }
                        StringBuffer buffer = new StringBuffer();
                        while (cursor.moveToNext()) {
                            buffer.append("Zasób: " + cursor.getString(1) + "\n");
                            buffer.append("Nazwa: "+ cursor.getString(3) + "\n");
                            buffer.append("Kwota: " + cursor.getString(2) + "\n");
                            buffer.append("\n");
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
                Intent intent = new Intent(IncomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            if(extras.containsKey("Settings")) {
                Intent intent = new Intent(IncomeActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            }
        }
        cursor.close();
    }

    void onDodaj() {
        String kwotapole = polekwota.getText().toString();
        zasob = "Gotówka";
        String gotowkapole = polegotowka.getText().toString();

        if(!kwotapole.isEmpty()) {
            try
            {
                kwota = Integer.parseInt(polekwota.getText().toString());
            }catch(Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zapisane dane, prosimy edytować bądź usunąć przychód", Toast.LENGTH_SHORT).show();
            }
                if (bazadanych.dodajtekst2(zasob, kwota, gotowkapole)) {
                    polekwota.setText("");
                    polegotowka.setText("");
                }
            Toast.makeText(getApplicationContext(), "Dodano!",Toast.LENGTH_SHORT).show();
            }
        else {
            Toast.makeText(getApplicationContext(), "Błąd! Dane nie zostały wprowadzone.",Toast.LENGTH_SHORT).show();
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