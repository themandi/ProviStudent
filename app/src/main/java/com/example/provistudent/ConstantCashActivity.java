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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ConstantCashActivity extends AppCompatActivity {
    Cursor cursor;
    Bazadanych bazadanych;
    ListView listView;
    Button oplacwszystko;
    Button pomin;
    int data_aktualnaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constant_cash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        bazadanych = new Bazadanych(ConstantCashActivity.this);
        wypelnijliste();


        oplacwszystko = (Button) findViewById(R.id.oplacwszystko);
        oplacwszystko.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
               int kwota = bazadanych.sumawydatkowstalych();
               if(kwota != 0) {
                   Calendar cal = Calendar.getInstance();
                   SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
                   String data_aktualnastring =  data_aktualna.format(cal.getTime());
                   data_aktualnaint = Integer.parseInt(data_aktualnastring);
                   String wydatek = "Stały";
                   String cheatday = "Nie";
                   bazadanych.dodajtekst5(data_aktualnaint, wydatek, kwota, cheatday);
               }
                Intent intent = new Intent(ConstantCashActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });


        pomin = (Button) findViewById(R.id.pomin);
        pomin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(ConstantCashActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void wypelnijliste() {
        cursor = bazadanych.odczytajtekst3();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Brak wydatków stałych, proszę o wprowadzenie ich w ustawieniach użytkownika", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<String> lista = new ArrayList<>();
        while (cursor.moveToNext()) {
            lista.add("Wydatek: " + cursor.getString(1) + "\n" + "Kwota: " + cursor.getString(2) + "\n");
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);
        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}