package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditCashActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;
    Button przyciskusun;
    Button przyciskwyswietl;
    Button przyciskedytuj;
    Bazadanych bazadanych;
    Cursor cursor;
    TextView polekwota;
    EditText wydanoedycja;
    String datadozapisu;
    int datadozapisuint;
    int kwota;
    String data;
    String dzienimiesiac;
    int dataint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bazadanych = new Bazadanych(EditCashActivity.this);
        cursor = bazadanych.odczytajtekst5();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        String data_aktualnastring =  data_aktualna.format(cal.getTime());
        dataint = Integer.parseInt(data_aktualnastring);

        Intent calendarz = getIntent();
        dzienimiesiac = calendarz.getStringExtra("data2");
        data = calendarz.getStringExtra("data");
        datadozapisu = calendarz.getStringExtra("datadozapisu");
        datadozapisuint = Integer.parseInt(datadozapisu);
        wydanoedycja = findViewById(R.id.wydanoedycja);
        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(EditCashActivity.this, DayofcalendarActivity.class);
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
                String wydatek = wydanoedycja.getText().toString();
                String cheatday = "Nie";
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
                        if (bazadanych.zaaktualizujtekst5(datadozapisuint, wydatek, kwota, cheatday)) {
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
            Intent intent = new Intent(EditCashActivity.this, DayofcalendarActivity.class);
            intent.putExtra("data",data);
            intent.putExtra("data2",dzienimiesiac);
            intent.putExtra("datadozapisu",datadozapisu);
            startActivity(intent);
        }
        cursor.close();
    }

    void onDodaj() {
        // Odczytanie informacji z EditText
        String kwotapole = polekwota.getText().toString();
        try
        {
            kwota = Integer.parseInt(polekwota.getText().toString());
        }catch(Throwable t)
        {
            Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zapisane dane, prosimy edytować bądź usunąć wydatek", Toast.LENGTH_SHORT).show();
        }
        String wydatek = wydanoedycja.getText().toString();
        // Zmienne potrzebne do odczytania oraz zaaktualizowania bazy danych
        String cheatday = "Nie";
        String wydatekbaza;
        String databaza;
        int licznik = 0;
        String wydatekstring = "Automatyczny";
        Integer ID;
        // Odczytanie informacji z bazy danych
        cursor = bazadanych.odczytajtekst5();
        // Zabezpieczenie przed przedwczesnym wprowadzeniem danych o wydatkach
        if (datadozapisuint <= dataint) {
            // Jeśli nie ma żadnych wydatków
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Brak wydatków!", Toast.LENGTH_SHORT).show();
            } else {
                // Odczytywanie wydatków z bazy danych
                while (cursor.moveToNext()) {
                    wydatekbaza = cursor.getString(cursor.getColumnIndex("wydatek"));
                    databaza = cursor.getString(cursor.getColumnIndex("data"));
                    // Jeśli wydatek jest automatyczny i data odpowiada dacie zaznaczonej w kalendarzu
                    if (wydatekbaza.equals(wydatekstring) && databaza.equals(datadozapisu)) {
                        ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        // Zabezpieczenie przed wprowadzeniem pustej kwoty wydatku
                        if (!kwotapole.isEmpty()) {
                            if (bazadanych.zaaktualizujtekstcash(ID, datadozapisuint, wydatek, kwota, cheatday)) {
                                polekwota.setText("");
                                licznik = 1;
                            }
                            Toast.makeText(getApplicationContext(), "Edytowano!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Błąd! Dane nie zostały wprowadzone.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                // Jeśli nie znaleziono wydatku automatycznego wydatku, można dodać wydatek
                if (licznik == 0) {
                    if (!kwotapole.isEmpty()) {
                        if (bazadanych.dodajtekst5(datadozapisuint, wydatek, kwota, cheatday)) {
                            polekwota.setText("");
                        }
                        Toast.makeText(getApplicationContext(), "Dodano!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Błąd! Dane nie zostały wprowadzone.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            // Zamknięcie kursora
            cursor.close();
        } else {
            Toast.makeText(getApplicationContext(), "Nie możesz dodać wcześniej wydatków!", Toast.LENGTH_SHORT).show();
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