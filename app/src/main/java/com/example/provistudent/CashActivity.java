package com.example.provistudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CashActivity extends AppCompatActivity {
    Button przyciskzapisz;
    Button przyciskcofnij;
    Button przyciskdodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        przyciskcofnij = (Button) findViewById(R.id.cofnij);
        przyciskcofnij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(CashActivity.this, RegisterActivity.class);
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

        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w oplatach stalych
        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.wybordochodu, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        Toast.makeText(parent.getContext(), "Prosze wybrać jedną z opcji!", Toast.LENGTH_SHORT).show();
//                        Wymyśl pomysł co zrobić aby nie można bylo zaznaczyć tej opcji
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
                        Intent intent = new Intent(CashActivity.this, NewCashActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
            }
        });
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        Intent intent = new Intent(CashActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do zapisywania
    }
    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onDodaj() {
        Intent intent = new Intent(CashActivity.this, MainActivity.class);
        startActivity(intent);
        //Metoda do dodawania
    }
}