package com.example.provistudent.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.Helpers.Notifications;
import com.example.provistudent.R;
import com.example.provistudent.Pickers.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.MenuItem;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private Notifications powiadomienia;
    Bazadanych bazadanych;
    Button przyciskzapisz;
    Button przypominaczgodzina;
    Button przypominaczdzien;
    Cursor cursor;
    Cursor cursor2;
    CheckBox checkoplaty;
    CheckBox checkoplatykiedy;
    CheckBox checkoszczednosci;
    CheckBox checkdane;
    CheckBox usunpowiad;
    CheckBox usunpowiadoplaty;
    Spinner spinnerdochod;
    TextView poleoszczednosci;
    TextView powiadomieniagodzina;
    TextView powiadomieniadzien;
    TextView powiadomieniadzien2;
    String wybrano = "No";
    String wybrano2 = "No";
    String wybrano3 = "No";
    String wybrano4 = "No";
    String wybrano5 = "No";
    String wybrano6 = "No";
    EditText poleimie;
    DialogFragment timePicker;
    DialogFragment timePicker2;
    DatePickerDialog datepicker;
    String czestotliwoscopcje;
    String time = "";
    String time2 = "";
    String date = "";
    String timezmienna = "";
    long interwal = 0;
    String czywlaczone = "No";
    String czywlaczone2 = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        poleimie = (EditText) findViewById(R.id.poleimie);
        przyciskzapisz = findViewById(R.id.zapisz);
        checkoplaty = findViewById(R.id.checkoplaty);
        checkoplatykiedy = findViewById(R.id.checkoplatykiedy);
        checkoszczednosci = findViewById(R.id.checkoszczednosci);
        checkdane = findViewById(R.id.checkdane);
        usunpowiad = findViewById(R.id.usunpowiad);
        usunpowiadoplaty = findViewById(R.id.usunpowiadoplaty);
        poleoszczednosci = findViewById(R.id.poleoszczednosci);

        bazadanych = new Bazadanych(SettingsActivity.this);
        powiadomienia = new Notifications(bazadanych, (AlarmManager)getSystemService(Context.ALARM_SERVICE), getApplicationContext());

        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onZapisz();
            }
        });

        Button ustawienia;
        ustawienia = findViewById(R.id.ustawienia);
        ustawienia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aby zmienić ustawienia aplikacji należy edytować tylko to pole, które nas interesuje. Pozostałe twoje dane nie będą mieć wprowadzonych zmian!", Toast.LENGTH_LONG).show();
            }
        });

        cursor = bazadanych.odczytajtekst();
        while(cursor.moveToNext()) {
            String zapisaneimie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
            poleimie.setText(zapisaneimie);
        }
        cursor.close();

        cursor = bazadanych.odczytajtekst();
        while(cursor.moveToNext()) {
            String checkoplatystring = cursor.getString(cursor.getColumnIndex("checkoplaty"));
            String checkoplatykiedystring = cursor.getString(cursor.getColumnIndex("checkoplatykiedy"));
            String checkoszczednoscistring = cursor.getString(cursor.getColumnIndex("checkoszczednosci"));
            String checkdanestring = cursor.getString(cursor.getColumnIndex("checkdane"));
            if(checkoplatystring.equals("Yes")) {
                checkoplaty.setChecked(true);
            }
            if(checkoplatykiedystring.equals("Yes")) {
                checkoplatykiedy.setChecked(true);
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
            if(checkoszczednoscistring.equals("Yes")) {
                checkoszczednosci.setChecked(true);
                LinearLayout linearlayout6 = findViewById(R.id.linearlayout6);
                View view5 = findViewById(R.id.view5);
                View view6 = findViewById(R.id.view6);
                linearlayout6.setVisibility(View.VISIBLE);
                view5.setVisibility(View.VISIBLE);
                view6.setVisibility(View.VISIBLE);
            }
            if(checkdanestring.equals("Yes")) {
                LinearLayout linearlayout7 = findViewById(R.id.linearlayout7);
                View view7 = findViewById(R.id.view7);
                View view8 = findViewById(R.id.view8);
                linearlayout7.setVisibility(View.VISIBLE);
                view7.setVisibility(View.VISIBLE);
                view8.setVisibility(View.VISIBLE);
                checkdane.setChecked(true);
            }
        }
        cursor.close();

        cursor = bazadanych.odczytajtekst();
        while(cursor.moveToNext()) {
            String zapisaneoszcz = cursor.getString(cursor.getColumnIndex("oszczednosci"));
            poleoszczednosci.setText(zapisaneoszcz);
        }
        cursor.close();

        powiadomieniadzien = (TextView) findViewById(R.id.powiadomieniadzien);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                String powiadomieniadzienbaza = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                if (powiadomieniadzienbaza == null || powiadomieniadzienbaza.equals("")) {
                    powiadomieniadzien.setText("Brak");
                } else {
                    SimpleDateFormat datebaza = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat datestring = new SimpleDateFormat("dd/MM/yyyy");
                    Date data = null;
                    try {
                        data = datebaza.parse(powiadomieniadzienbaza);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String dataformat = datestring.format(data);
                    powiadomieniadzien.setText(dataformat);
                }
            }
        }
        cursor2.close();

        powiadomieniadzien2 = (TextView) findViewById(R.id.powiadomieniadzien2);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                String powiadomieniadzientime = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                if (powiadomieniadzientime == null || powiadomieniadzientime.equals("")) {
                    powiadomieniadzien2.setText("Brak");
                } else {
                    SimpleDateFormat timebaza = new SimpleDateFormat("HHmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("HH:mm");
                    Date time = null;
                    try {
                        time = timebaza.parse(powiadomieniadzientime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeformat = timestring.format(time);
                    powiadomieniadzien2.setText(timeformat);
                }
            }
        }
        cursor2.close();

        powiadomieniagodzina = (TextView) findViewById(R.id.powiadomieniagodzina);
        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                String powiadomieniagodz = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                if (powiadomieniagodz == null || powiadomieniagodz.equals("")) {
                    powiadomieniagodzina.setText("Brak");
                } else {
                    SimpleDateFormat timebaza = new SimpleDateFormat("HHmm");
                    SimpleDateFormat timestring = new SimpleDateFormat("HH:mm");
                    Date time = null;
                    try {
                        time = timebaza.parse(powiadomieniagodz);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeformat = timestring.format(time);
                    powiadomieniagodzina.setText(timeformat);
                }
            }
        }
        cursor2.close();

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat oraz zapisywaniu do bazy danych
        checkoplaty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano = "Yes";
                } else {
                    wybrano = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat kiedy oraz zapisywaniu do bazy danych
        checkoplatykiedy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano2 = "Yes";
                } else {
                    wybrano2 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie oszczednosci oraz zapisywaniu do bazy danych
        checkoszczednosci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano3 = "Yes";
                } else {
                    wybrano3 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie danych oraz zapisywaniu do bazy danych
        checkdane.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano4 = "Yes";
                } else {
                    wybrano4 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat oraz zapisywaniu do bazy danych
        usunpowiadoplaty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano5 = "Yes";
                } else {
                    wybrano5 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat oraz zapisywaniu do bazy danych
        usunpowiad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if (zaznaczone) {
                    wybrano6 = "Yes";
                } else {
                    wybrano6 = "No";
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
                    case 1:
                        Intent intent = new Intent(SettingsActivity.this, IncomeActivity.class);
                        intent.putExtra("Settings", "SettingsActivity");
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(SettingsActivity.this, IncomeActivity2.class);
                        intent2.putExtra("Settings", "SettingsActivity");
                        startActivity(intent2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                czestotliwoscopcje = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 1:
//                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co miesiąc", Toast.LENGTH_SHORT).show();
                        interwal = 2592000000L;
                        break;
                    case 2:
//                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co trzy miesiące", Toast.LENGTH_SHORT).show();
                        interwal = 7776000000L;
                        break;
                    case 3:
//                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co pół roku", Toast.LENGTH_SHORT).show();
                        interwal = 15552000000L;
                        break;
                    case 4:
//                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co rok", Toast.LENGTH_SHORT).show();
                        interwal = 31104000000L;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cursor2 = bazadanych.odczytajtekst4();
        if(cursor2 != null && cursor2.getCount() > 0) {
            String coilepowiad = "Brak";
            while (cursor2.moveToNext()) {
                coilepowiad = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                if (coilepowiad.equals("Co miesiąc")) {
                    spinner2.setSelection(adapter2.getPosition("Co miesiąc"));
                }
                if (coilepowiad.equals("Co trzy miesiące")) {
                    spinner2.setSelection(adapter2.getPosition("Co trzy miesiące"));
                }
                if (coilepowiad.equals("Co pół roku")) {
                    spinner2.setSelection(adapter2.getPosition("Co pół roku"));
                }
                if (coilepowiad.equals("Co rok")) {
                    spinner2.setSelection(adapter2.getPosition("Co rok"));
                }
            }
        }
        cursor2.close();


//      przypominacz godziny
        przypominaczgodzina = (Button) findViewById(R.id.przypominaczgodzina);
        przypominaczgodzina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new TimePicker();
                timezmienna = "picker1";
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
//      przypominacz czasu
        przypominaczdzien = (Button) findViewById(R.id.przypominaczdzien);
        przypominaczdzien.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                showTimePickerDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void showDatePickerDialog() {
        datepicker = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datepicker.show();
    }

    private void showTimePickerDialog() {
        timePicker2 = new TimePicker();
        timezmienna = "picker2";
        timePicker2.show(getSupportFragmentManager(), "time picker");
    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        String imie = poleimie.getText().toString();
        cursor = bazadanych.odczytajtekst();
        if(imie == null || imie.equals("")) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    imie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
                }
            }
        }
        cursor.close();
        int oszczednosci = 0;

        if (wybrano2.equals("Yes")) {
            String data = date;
            String godzina2 = time2;
            String godzina = null;
            czywlaczone = "Yes";
            cursor2 = bazadanych.odczytajtekst4();
            if(czestotliwoscopcje.equals("Wybierz") || czestotliwoscopcje.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        czestotliwoscopcje = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if(interwal == 0) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if(czywlaczone2.equals("No")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        czywlaczone2 = cursor2.getString(cursor2.getColumnIndex("czywlaczone2"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (data.equals("") || data == null) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (godzina2 == null || godzina2.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if(godzina == null || godzina.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    }
                }
            }
            if (cursor2.getCount() == 0) {
                bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            } else {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();

            if(!date.equals("") && !time2.equals("")) {
                powiadomienia.ustawNowePowiadomieniaWAlarmManager1();
            }
        }

        cursor = bazadanych.odczytajtekst();
        if (wybrano3.equals("Yes")) {
            try
            {
                oszczednosci = Integer.parseInt(poleoszczednosci.getText().toString());
            }catch(NumberFormatException e)
            {
                e.printStackTrace();
            }
            if (poleoszczednosci == null || poleoszczednosci.equals("")) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        oszczednosci = cursor.getInt(cursor.getColumnIndex("oszczednosci"));
                    }
                }
            }
        }
        cursor.close();

        if (wybrano4.equals("Yes")) {
            String godzina = time;
            String data = null;
            String godzina2 = null;
            czywlaczone2 = "Yes";
            cursor2 = bazadanych.odczytajtekst4();
            if (godzina == null || godzina.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if(czywlaczone.equals("No")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        czywlaczone = cursor2.getString(cursor2.getColumnIndex("czywlaczone"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                    }
                }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    czestotliwoscopcje = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() == 0) {
                bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            } else {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();

            if(!time.equals("")) {
                powiadomienia.ustawNowePowiadomieniaWAlarmManager2();
            }
        }

        cursor = bazadanych.odczytajtekst();
        if(oszczednosci == 0) {
            if (cursor.getCount() > 0) {
                if(cursor.moveToNext()) {
                    oszczednosci = cursor.getInt(cursor.getColumnIndex("oszczednosci"));
                }
            }
        }
        cursor.close();

        cursor = bazadanych.odczytajtekst();
        if (cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                if(wybrano.equals("No")) {
                 wybrano = cursor.getString(cursor.getColumnIndex("checkoplaty"));
                }
                if(wybrano2.equals("No")) {
                    wybrano2 = cursor.getString(cursor.getColumnIndex("checkoplatykiedy"));
                }
                if(wybrano3.equals("No")) {
                    wybrano3 = cursor.getString(cursor.getColumnIndex("checkoszczednosci"));
                }
                if(wybrano4.equals("No")) {
                    wybrano4 = cursor.getString(cursor.getColumnIndex("checkdane"));
                }
            }
            if (bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci)) {
                poleimie.setText("");
                poleoszczednosci.setText("");
            }
        }
        cursor.close();

        if (wybrano2.equals("Yes") && wybrano4.equals("Yes")) {
            String data = date;
            String godzina2 = time2;
            String godzina = time;
            czywlaczone = "Yes";
            czywlaczone2 = "Yes";
            cursor2 = bazadanych.odczytajtekst4();
            if(czestotliwoscopcje.equals("") || czestotliwoscopcje == null || czestotliwoscopcje.equals("Wybierz")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        czestotliwoscopcje = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if(interwal == 0) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (data == null || data.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (godzina2 == null || godzina2.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (godzina == null || godzina.equals("")) {
                if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext()) {
                        godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    }
                }
            }
            cursor2.close();

            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() == 0) {
                bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            } else {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();
            if(!time2.equals("") && !date.equals("")) {
                powiadomienia.ustawNowePowiadomieniaWAlarmManager1();
            }
            if(!time.equals("")) {
                powiadomienia.ustawNowePowiadomieniaWAlarmManager2();
            }
        }

        if(wybrano5.equals("Yes")) {
            powiadomienia.usunPowiadomieniaZAlarmManager1();
            czywlaczone = "No";
            wybrano = "No";
            wybrano2 = "No";
            String data = null;
            String godzina2 = null;
            String godzina = null;
            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    czywlaczone2 = cursor2.getString(cursor2.getColumnIndex("czywlaczone2"));
                    godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                    data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                    interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Alarm nie był ustawiony!",Toast.LENGTH_SHORT).show();
            }
            if (cursor2.getCount() > 0) {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();

            cursor = bazadanych.odczytajtekst();
            if(cursor.getCount() > 0) {
                bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci);
            }
            cursor.close();
        }

        if(wybrano6.equals("Yes")) {
            powiadomienia.usunPowiadomieniaZAlarmManager2();
            czywlaczone2 = "No";
            wybrano4 = "No";
            String data = null;
            String godzina2 = null;
            String godzina = null;
            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    czywlaczone = cursor2.getString(cursor2.getColumnIndex("czywlaczone"));
                    godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                    interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                    czestotliwoscopcje = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                    data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Alarm nie był ustawiony!",Toast.LENGTH_SHORT).show();
            }
            if (cursor2.getCount() > 0) {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();

            cursor = bazadanych.odczytajtekst();
            if(cursor.getCount() > 0) {
                bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci);
            }
            cursor.close();
        }
        if(wybrano5.equals("Yes") && wybrano6.equals("Yes")) {
            powiadomienia.usunPowiadomieniaZAlarmManager1();
            powiadomienia.usunPowiadomieniaZAlarmManager2();
            czywlaczone = "No";
            czywlaczone2 = "No";
            wybrano = "No";
            wybrano2 = "No";
            wybrano4 = "No";
            String data = null;
            String godzina2 = null;
            String godzina = null;
            cursor2 = bazadanych.odczytajtekst4();
            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    godzina = cursor2.getString(cursor2.getColumnIndex("kiedydane"));
                    godzina2 = cursor2.getString(cursor2.getColumnIndex("kiedypowczas"));
                    data = cursor2.getString(cursor2.getColumnIndex("kiedypow"));
                    interwal = cursor2.getLong(cursor2.getColumnIndex("interwal"));
                    czestotliwoscopcje = cursor2.getString(cursor2.getColumnIndex("czestotliwosc"));
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Alarm nie był ustawiony!",Toast.LENGTH_SHORT).show();
            }
            if (cursor2.getCount() > 0) {
                bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
            }
            cursor2.close();

            cursor = bazadanych.odczytajtekst();
            if(cursor.getCount() > 0) {
                bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci);
            }
            cursor.close();
        }

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String minutestring = "" + minute;
        String hourstring = "" + hourOfDay;
        if (hourOfDay < 10) {
            hourstring = "0" + hourstring;
        }
        if (minute < 10) {
            minutestring = "0" + minutestring;
        }
        if (TextUtils.isEmpty(timezmienna))
            return;
        if (timezmienna.equalsIgnoreCase("picker1")) {
            powiadomieniagodzina.setText("Czas: " + hourstring + ":" + minutestring);
            time = hourstring + "" + minutestring;
        } else if (timezmienna.equalsIgnoreCase("picker2")) {
            powiadomieniadzien2.setText("Czas: " + hourstring + ":" + minutestring);
            time2 = hourstring + "" + minutestring;
        }
        timezmienna = "";
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String monthstring = "" + month;
        String daystring = "" + dayOfMonth;
        if (month < 10) {
            monthstring = "0" + monthstring;
        }
        if (dayOfMonth < 10) {
            daystring = "0" + daystring;
        }
        powiadomieniadzien.setText("Data: " + daystring + "/" + monthstring + "/" + year);
        date = year + "" + monthstring + "" + daystring;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCheckboxClicked(View view) {
        switch (view.getId()) {
            case R.id.checkoplaty:
                boolean zaznaczony = ((CheckBox) view).isChecked();
                if (zaznaczony == true) {
                    Intent intent = new Intent(SettingsActivity.this, CashActivity.class);
                    intent.putExtra("Settings", "SettingsActivity");
                    startActivity(intent);
                }
                break;
            case R.id.checkoplatykiedy:
                boolean zaznaczony2 = ((CheckBox) view).isChecked();
                if (zaznaczony2 == true) {
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
                } else {
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
                boolean zaznaczony3 = ((CheckBox) view).isChecked();
                if (zaznaczony3 == true) {
                    LinearLayout linearlayout6 = findViewById(R.id.linearlayout6);
                    View view5 = findViewById(R.id.view5);
                    View view6 = findViewById(R.id.view6);
                    linearlayout6.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                    view6.setVisibility(View.VISIBLE);
                } else {
                    LinearLayout linearlayout6 = findViewById(R.id.linearlayout6);
                    View view5 = findViewById(R.id.view5);
                    View view6 = findViewById(R.id.view6);
                    linearlayout6.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    view6.setVisibility(View.GONE);
                }

                break;
            case R.id.checkdane:
                boolean zaznaczony4 = ((CheckBox) view).isChecked();
                if (zaznaczony4 == true) {
                    LinearLayout linearlayout7 = findViewById(R.id.linearlayout7);
                    View view7 = findViewById(R.id.view7);
                    View view8 = findViewById(R.id.view8);
                    linearlayout7.setVisibility(View.VISIBLE);
                    view7.setVisibility(View.VISIBLE);
                    view8.setVisibility(View.VISIBLE);
                } else {
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