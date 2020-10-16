package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IncomeActivity2 extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income2);
        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(IncomeActivity2.this, RegisterActivity.class);
                startActivity(intent);
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
        Intent intent = new Intent(IncomeActivity2.this, MainActivity.class);
        startActivity(intent);
        //Metoda do zapisywania
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onDodaj() {
        Intent intent = new Intent(IncomeActivity2.this, MainActivity.class);
        startActivity(intent);
        //Metoda do dodawania
    }
}