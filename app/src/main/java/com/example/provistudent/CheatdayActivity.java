package com.example.provistudent;

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

public class CheatdayActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    Spinner spinner3;
    TextView polekwota;
    String wydatek;
    int kwota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheatday);

        bazadanych = new Bazadanych(CheatdayActivity.this);
        cursor = bazadanych.odczytajtekst5();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner3 = findViewById(R.id.spinner3);
        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(CheatdayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        przyciskusun = (Button) findViewById(R.id.usun);
        przyciskusun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Integer usunwiersz = bazadanych.usuntekst5(polekwota.getText().toString());
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
                        kwota = Integer.parseInt(polekwota.getText().toString());
                        String cheatday = "Yes";
                        Intent calendarz = getIntent();
                        String data = calendarz.getStringExtra("data");
                        if (bazadanych.zaaktualizujtekst5(data, wydatek, kwota, cheatday)) {
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
                cursor = bazadanych.odczytajtekst5();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID: " + cursor.getString(0) + "\n");
                    buffer.append("Data: " + cursor.getString(1) + "\n");
                    buffer.append("Wydatek: " + cursor.getString(2) + "\n");
                    buffer.append("Kwota: " + cursor.getString(3) + "\n");
                }
                wyswietlwiadomosc("Zapisane wydatki: ", buffer.toString());
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
//                    case 1:
//                        Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(parent.getContext(), "Spinner item3!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4:
//                        Toast.makeText(parent.getContext(), "Spinner item 4!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 5:
//                        Toast.makeText(parent.getContext(), "Spinner item 5!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 6:
//                        Toast.makeText(parent.getContext(), "Spinner item 6!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 7:
//                        Toast.makeText(parent.getContext(), "Spinner item 7!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 8:
//                        Toast.makeText(parent.getContext(), "Spinner item 8!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 9:
//                        Toast.makeText(parent.getContext(), "Spinner item 9!", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 10:
//                        Toast.makeText(parent.getContext(), "Spinner item 10!", Toast.LENGTH_SHORT).show();
//                        break;
                    case 11:
                        Intent intent = new Intent(CheatdayActivity.this, NewCheatdayActivity.class);
                        startActivity(intent);
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
            Intent intent = new Intent(CheatdayActivity.this, MainActivity.class);
            startActivity(intent);
        }
        cursor.close();
    }

    void onDodaj() {
        String kwotapole = polekwota.getText().toString();
        if(!kwotapole.isEmpty()) {
            kwota = Integer.parseInt(polekwota.getText().toString());
            String cheatday = "Yes";
            Intent calendarz = getIntent();
            String data = calendarz.getStringExtra("data");
            if (bazadanych.dodajtekst5(data, wydatek, kwota, cheatday)) {
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