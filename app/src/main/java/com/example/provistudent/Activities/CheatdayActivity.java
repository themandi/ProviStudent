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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    String datadozapisu;
    int datadozapisuint;
    String dzienimiesiac;
    String data;
    String zasob;
    Spinner spinnerzaplata;
    int data_aktualnaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheatday);
        bazadanych = new Bazadanych(CheatdayActivity.this);
        cursor = bazadanych.odczytajtekst5();
        Intent calendarz = getIntent();
        dzienimiesiac = calendarz.getStringExtra("data2");
        data = calendarz.getStringExtra("data");
        datadozapisu = calendarz.getStringExtra("datadozapisu");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        String data_aktualnastring =  data_aktualna.format(cal.getTime());
        data_aktualnaint = Integer.parseInt(data_aktualnastring);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner3 = findViewById(R.id.spinner3);
        spinnerzaplata = findViewById(R.id.spinnerzaplata);
        polekwota = findViewById(R.id.polekwota);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = getIntent();
                Bundle extras = i.getExtras();
                if (extras.containsKey("Main")) {
                    Intent intent = new Intent(CheatdayActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if (extras.containsKey("Calendar")) {
                    Intent intent = new Intent(CheatdayActivity.this, DayofcalendarActivity.class);
                    startActivity(intent);
                }
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
                        try
                        {
                            kwota = Integer.parseInt(polekwota.getText().toString());
                        }catch(Throwable t)
                        {
                            Toast.makeText(getApplicationContext(), "Error: Niepoprawnie zapisane dane, prosimy spróbować ponownie!", Toast.LENGTH_SHORT).show();
                        }
                        String cheatday = "Tak";
                        Intent i = getIntent();
                        Bundle extras = i.getExtras();
                        if (extras.containsKey("Main")) {
                            if (bazadanych.zaaktualizujtekst5(data_aktualnaint, wydatek, kwota, cheatday, zasob)) {
                                polekwota.setText("");
                                Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (extras.containsKey("Calendar")) {
                            datadozapisuint = Integer.parseInt(datadozapisu);
                            if (bazadanych.zaaktualizujtekst5(datadozapisuint, wydatek, kwota, cheatday, zasob)) {
                                polekwota.setText("");
                                Toast.makeText(getApplicationContext(), "Dane zostały zaaktualizowane!", Toast.LENGTH_SHORT).show();
                            }
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
                cursor = bazadanych.odczytajtekstcheatday5();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc("Error", "Brak zapisanych zasobów!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    String powiadomieniadzienbaza = cursor.getString(cursor.getColumnIndex("data"));
                    if (powiadomieniadzienbaza != null || !powiadomieniadzienbaza.equals("")) {
                        SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                        Date data = null;
                        try {
                            data = datebaza.parse(powiadomieniadzienbaza);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String dataformat = datestring.format(data);

                        buffer.append("ID: " + cursor.getString(0) + "\n");
                        buffer.append("Data: " + dataformat + "\n");
                        buffer.append("Wydatek: " + cursor.getString(2) + "\n");
                        buffer.append("Kwota: " + cursor.getString(3) + "\n");
                        buffer.append("Zapłata za pomocą: " + cursor.getString(5) + "\n");
                    }
                }
                wyswietlwiadomosc("Wydatki za pomocą funkcji 'Cheatday': ", buffer.toString());
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
                R.array.wyborcheatday, android.R.layout.simple_spinner_item);
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
                    case 6:
                        Intent intent = new Intent(CheatdayActivity.this, NewCheatdayActivity.class);
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
            Intent intent = new Intent(CheatdayActivity.this, MainActivity.class);
            startActivity(intent);
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
            String cheatday = "Tak";
            Intent i = getIntent();
            Bundle extras = i.getExtras();
            if(extras.containsKey("Main")) {
                if (bazadanych.dodajtekst5(data_aktualnaint, wydatek, kwota, cheatday, zasob)) {
                    polekwota.setText("");
                }
            }
            if(extras.containsKey("Calendar")) {
                try
                {
                    datadozapisuint = Integer.parseInt(datadozapisu);
                }catch(Throwable t)
                {
                    Toast.makeText(getApplicationContext(), "Error: Błąd zapisu daty z kalendarza!", Toast.LENGTH_SHORT).show();
                }
                if (bazadanych.dodajtekst5(datadozapisuint, wydatek, kwota, cheatday, zasob)) {
                    polekwota.setText("");
                }
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