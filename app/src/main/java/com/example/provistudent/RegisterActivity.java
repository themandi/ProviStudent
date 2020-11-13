package com.example.provistudent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    Bazadanych bazadanych;
    EditText poleimie;
    Button przyciskzapisz;
    Button przypominaczgodzina;
    Button przypominaczdzien;
    Cursor cursor;
    CheckBox checkoplaty;
    CheckBox checkoplatykiedy;
    CheckBox checkoszczednosci;
    CheckBox checkdane;
    Spinner spinnerdochod;

    String wybranocheckoplaty = "No";
    String wybranocheckoplaty2 = "No";
    String wybranocheckoplaty3 = "No";
    String wybranocheckoplaty4 = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        poleimie = findViewById(R.id.poleimie);
        przyciskzapisz = findViewById(R.id.zapisz);
        checkoplaty = findViewById(R.id.checkoplaty);
        checkoplatykiedy= findViewById(R.id.checkoplatykiedy);
        checkoszczednosci = findViewById(R.id.checkoszczednosci);
        checkdane = findViewById(R.id.checkdane);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Baza danych
        //JAK SKONCZYSZ BAWIC SIE TUTAJ Z BAZA PROSZE USUN TE WYSWIETLENIA
        bazadanych = new Bazadanych(RegisterActivity.this);
        cursor = bazadanych.odczytajtekst();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No data",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext())
            {
                Toast.makeText(getApplicationContext(), "1: "+cursor.getString(1),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "2: "+cursor.getString(2),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "3: "+cursor.getString(3),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "4: "+cursor.getString(4),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "5: "+cursor.getString(5),Toast.LENGTH_SHORT).show();
            }
        }

        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                onZapisz();
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat oraz zapisywaniu do bazy danych
        checkoplaty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybranocheckoplaty = "Yes";
                }
                else {
                    wybranocheckoplaty = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat kiedy oraz zapisywaniu do bazy danych
        checkoplatykiedy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybranocheckoplaty2 = "Yes";
                }
                else {
                    wybranocheckoplaty2 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie oszczednosci oraz zapisywaniu do bazy danych
        checkoszczednosci.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybranocheckoplaty3 = "Yes";
                }
                else {
                    wybranocheckoplaty3 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie danych oraz zapisywaniu do bazy danych
        checkdane.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybranocheckoplaty4 = "Yes";
                }
                else {
                    wybranocheckoplaty4 = "No";
                }
            }
        });


        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w Dochodzie
        spinnerdochod = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.srodki, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdochod.setAdapter(adapter);
        spinnerdochod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
//                    case 0:
//                        Toast.makeText(parent.getContext(), "Proszę wybrać jedną z opcji", Toast.LENGTH_SHORT).show();
//                        Wymyśl pomysł co zrobić aby nie można bylo zaznaczyć tej opcji
//                        break;
                    case 1:
                        Intent intent = new Intent(RegisterActivity.this, IncomeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(RegisterActivity.this, IncomeActivity2.class);
                        startActivity(intent2);
                        break;
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }
            });

        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w Częstotliwości powiadomień
        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.czestotliwosc, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        Toast.makeText(parent.getContext(), "Prosze wybrać jedną z opcji!", Toast.LENGTH_SHORT).show();
//                        Wymyśl pomysł co zrobić aby nie można bylo zaznaczyć tej opcji
                        break;
                    case 1:
                        Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
//                        Sprawdzic czy to musi byc register czy main, w classie ta klasa mala do wystartowania
//                        Intent intent = new Intent(RegisterActivity.this, CashActivity.class);
//                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Spinner item 4!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Spinner item 5!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
            }
        });

//      przypominacz godziny
        przypominaczgodzina = (Button) findViewById(R.id.przypominaczgodzina);
        przypominaczgodzina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
//      przypominacz czasu
        przypominaczdzien = (Button) findViewById(R.id.przypominaczdzien);
        przypominaczdzien.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

            private void showDatePickerDialog() {
                DatePickerDialog datepicker = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datepicker.show();
            }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        String imie = poleimie.getText().toString();
        if(!imie.isEmpty()) {
            if(cursor.getCount()==0) {
                if (bazadanych.dodajtekst(imie, wybranocheckoplaty, wybranocheckoplaty2, wybranocheckoplaty3, wybranocheckoplaty4)) {
                    poleimie.setText("");
                }
            }
            else if(cursor.getCount()>0) {
                if (bazadanych.zaaktualizujtekst(imie, wybranocheckoplaty, wybranocheckoplaty2, wybranocheckoplaty3, wybranocheckoplaty4)) {
                    poleimie.setText("");
                }
            }
        }
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView powiadomieniagodzina = (TextView) findViewById(R.id.powiadomieniagodzina);
        powiadomieniagodzina.setText("Czas: " + hourOfDay + ":" + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView powiadomieniadzien = (TextView) findViewById(R.id.powiadomieniadzien);
        powiadomieniadzien.setText("Data: "+ dayOfMonth + "/" + month + "/" + year);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCheckboxClicked(View view) {
        switch(view.getId()) {
            case R.id.checkoplaty:
                boolean zaznaczony =((CheckBox)view).isChecked();
                if(zaznaczony == true)
                {
                    Intent intent = new Intent(RegisterActivity.this, CashActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.checkoplatykiedy:
                boolean zaznaczony2 =((CheckBox)view).isChecked();
                if(zaznaczony2 == true) {
                    LinearLayout linearlayout4 = findViewById(R.id.linearlayout4);
                    LinearLayout linearlayout5 = findViewById(R.id.linearlayout5);
                    View view1 = findViewById(R.id.view1);
                    View view2 = findViewById(R.id.view2);
                    View view3 = findViewById(R.id.view3);
                    View view4 = findViewById(R.id.view4);
                    linearlayout4.setVisibility(View.VISIBLE);
                    linearlayout5.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                }
                else {
                    LinearLayout linearlayout4 = findViewById(R.id.linearlayout4);
                    LinearLayout linearlayout5 = findViewById(R.id.linearlayout5);
                    View view1 = findViewById(R.id.view1);
                    View view2 = findViewById(R.id.view2);
                    View view3 = findViewById(R.id.view3);
                    View view4 = findViewById(R.id.view4);
                    linearlayout4.setVisibility(View.GONE);
                    linearlayout5.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                }
                break;

            case R.id.checkoszczednosci:
                boolean zaznaczony3 =((CheckBox)view).isChecked();
                if(zaznaczony3 == true) {
                    LinearLayout linearlayout6 = findViewById(R.id.linearlayout6);
                    View view5 = findViewById(R.id.view5);
                    View view6 = findViewById(R.id.view6);
                    linearlayout6.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                    view6.setVisibility(View.VISIBLE);
                }
                else {
                    LinearLayout linearlayout6 = findViewById(R.id.linearlayout6);
                    View view5 = findViewById(R.id.view5);
                    View view6 = findViewById(R.id.view6);
                    linearlayout6.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    view6.setVisibility(View.GONE);
                }

                break;
            case R.id.checkdane:
                boolean zaznaczony4 =((CheckBox)view).isChecked();
                if(zaznaczony4 == true) {
                    LinearLayout linearlayout7 = findViewById(R.id.linearlayout7);
                    View view7 = findViewById(R.id.view7);
                    View view8 = findViewById(R.id.view8);
                    linearlayout7.setVisibility(View.VISIBLE);
                    view7.setVisibility(View.VISIBLE);
                    view8.setVisibility(View.VISIBLE);
                }
                else {
                    LinearLayout linearlayout7 = findViewById(R.id.linearlayout7);
                    View view7 = findViewById(R.id.view7);
                    View view8 = findViewById(R.id.view8);
                    linearlayout7.setVisibility(View.GONE);
                    view7.setVisibility(View.GONE);
                    view8.setVisibility(View.GONE);
                }
                break;
        }
    }
}
