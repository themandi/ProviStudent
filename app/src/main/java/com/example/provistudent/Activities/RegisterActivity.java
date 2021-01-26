package com.example.provistudent.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.Helpers.Notifications;
import com.example.provistudent.R;
import com.example.provistudent.Pickers.TimePicker;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    Notifications powiadomienia;
    Bazadanych bazadanych;
    EditText poleimie;
    Button przyciskzapisz;
    Button przypominaczgodzina;
    Button przypominaczdzien;
    Cursor cursor;
    Cursor cursor2;
    CheckBox checkoplaty;
    CheckBox checkoplatykiedy;
    CheckBox checkoszczednosci;
    CheckBox checkdane;
    Spinner spinnerdochod;
    TextView poleoszczednosci;
    TextView powiadomieniagodzina;
    TextView powiadomieniadzien;
    TextView powiadomieniadzien2;
    String czestotliwoscopcje;
    String wybrano = "No";
    String wybrano2 = "No";
    String wybrano3 = "No";
    String wybrano4 = "No";
    String data;
    String godzina;
    String godzina2;
    DialogFragment timePicker;
    DialogFragment timePicker2;
    DatePickerDialog datepicker;
    String time;
    String time2;
    String date;
    long interwal;
    String timezmienna = "";
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
        poleoszczednosci = findViewById(R.id.poleoszczednosci);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bazadanych = new Bazadanych(RegisterActivity.this);
        powiadomienia = new Notifications(bazadanych, (AlarmManager)getSystemService(Context.ALARM_SERVICE), getApplicationContext());

        przyciskzapisz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                onZapisz();
            }
        });

        Button rejestracja;
        rejestracja = findViewById(R.id.rejestracja);
        rejestracja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aby przeprowadzić poprawnie rejestracje należy wypełnić dane o które jesteśmy zapytani. Jeśli zaznaczymy pole wyboru, musimy wprowadzić wszystkie dane, o które zostaniemy poproszeni. Zaznaczenie pytań dotyczących przypomnień powoduje włączenie powiadomień aplikacji.", Toast.LENGTH_LONG).show();
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat oraz zapisywaniu do bazy danych
        checkoplaty.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybrano = "Yes";
                }
                else {
                    wybrano = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie Opłat kiedy oraz zapisywaniu do bazy danych
        checkoplatykiedy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybrano2 = "Yes";
                }
                else {
                    wybrano2 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie oszczednosci oraz zapisywaniu do bazy danych
        checkoszczednosci.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybrano3 = "Yes";
                }
                else {
                    wybrano3 = "No";
                }
            }
        });

        //Metoda wykorzystywana do pobierania wybranej opcji w checkboxie danych oraz zapisywaniu do bazy danych
        checkdane.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean zaznaczone) {
                if(zaznaczone){
                    wybrano4 = "Yes";
                }
                else {
                    wybrano4 = "No";
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
                        Intent intent = new Intent(RegisterActivity.this, IncomeActivity.class);
                        intent.putExtra("Register", "RegisterActivity");
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(RegisterActivity.this, IncomeActivity2.class);
                        intent2.putExtra("Register", "RegisterActivity");
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
                czestotliwoscopcje = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 1:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co miesiąc", Toast.LENGTH_SHORT).show();
                        interwal = 2592000000L;
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co trzy miesiące", Toast.LENGTH_SHORT).show();
                        interwal = 7776000000L;
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co pół roku", Toast.LENGTH_SHORT).show();
                        interwal = 15552000000L;
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co rok", Toast.LENGTH_SHORT).show();
                        interwal = 31104000000L;
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
    private void showDatePickerDialog() {
        datepicker = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
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
        cursor = bazadanych.odczytajtekst();
        cursor2 = bazadanych.odczytajtekst4();
        String imie = poleimie.getText().toString();
        int oszczednosci = 0;
        String czywlaczone = "No";
        String czywlaczone2 = "No";

        if(wybrano2.equals("Yes")) {
            data = date;
            godzina2 = time2;
            czywlaczone = "Yes";
        }
        if(wybrano2.equals("No")) {
            date = null;
            godzina2 = null;
            czywlaczone = "No";
            powiadomienia.usunPowiadomieniaZAlarmManager1();
        }
        if(wybrano3.equals("Yes")) {
            try
            {
                oszczednosci = Integer.parseInt(poleoszczednosci.getText().toString());
            }catch(NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        if(wybrano4.equals("Yes")) {
            godzina = time;
            czywlaczone2 = "Yes";
        }
        if(wybrano4.equals("No")) {
            godzina = null;
            czywlaczone2 = "No";
            powiadomienia.usunPowiadomieniaZAlarmManager2();
        }
        if(!imie.isEmpty()) {
            if (cursor.getCount() == 0) {
                if (bazadanych.dodajtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci)) {
                    poleimie.setText("");
                    poleoszczednosci.setText("");
                }
            } else if (cursor.getCount() > 0) {
                if (bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci)) {
                    poleimie.setText("");
                    poleoszczednosci.setText("");
                }
            }
            if (wybrano2.equals("Yes") & wybrano4.equals("Yes")) {
                if (godzina2 != null && data != null && godzina != null && !czestotliwoscopcje.equals("Wybierz")) {
                    if (cursor2.getCount() == 0) {
                        bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    }
                    else if (cursor2.getCount() > 0) {
                        bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    }
                    powiadomienia.ustawNowePowiadomieniaWAlarmManager1();
                    powiadomienia.ustawNowePowiadomieniaWAlarmManager2();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                    introzobaczone();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie zostały wypełnione wszystkie pola powiadomień o opłatach i/lub danych", Toast.LENGTH_SHORT).show();
                }
            }
                else if (wybrano2.equals("Yes")) {
                godzina = null;
                if (data != null && godzina2 != null && !czestotliwoscopcje.equals("Wybierz")) {
                    if (cursor2.getCount() == 0) {
                        bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    } else {
                        bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    }
                    powiadomienia.ustawNowePowiadomieniaWAlarmManager1();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                    introzobaczone();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie zostały wypełnione wszystkie pola powiadomień o opłatach", Toast.LENGTH_SHORT).show();
                }
            } else if (wybrano4.equals("Yes")) {
                data = null;
                godzina2 = null;
                if (godzina != null) {
                    if (cursor2.getCount() == 0) {
                        bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    } else {
                        bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina, czywlaczone, interwal, czywlaczone2);
                    }
                    powiadomienia.ustawNowePowiadomieniaWAlarmManager2();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                    introzobaczone();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie zostały wypełnione wszystkie pola przypomnień o wprowadzeniu danych", Toast.LENGTH_SHORT).show();
                }
            }
                if(wybrano2.equals("No") && wybrano4.equals("No")) {
                    powiadomienia.usunPowiadomieniaZAlarmManager1();
                    powiadomienia.usunPowiadomieniaZAlarmManager2();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                    introzobaczone();
                }
        }
        else {
            Toast.makeText(getApplicationContext(), "Nic nie zostało zapisane! Sprawdź czy zaznaczyłeś obowiązkowe punkty w rejestracji",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        cursor2.close();
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String minutestring = "" + minute;
        String hourstring = "" + hourOfDay;
        if(hourOfDay < 10) {
            hourstring = "0" + hourstring;
        }
        if(minute < 10) {
            minutestring = "0" + minutestring;
        }
        if (TextUtils.isEmpty(timezmienna))
            return;
        if (timezmienna.equalsIgnoreCase("picker1")){
            powiadomieniagodzina = (TextView) findViewById(R.id.powiadomieniagodzina);
            powiadomieniagodzina.setText("Czas: " + hourstring + ":" + minutestring);
            time = hourstring + "" + minutestring;
        }
        else if (timezmienna.equalsIgnoreCase("picker2")){
            powiadomieniadzien2 = (TextView) findViewById(R.id.powiadomieniadzien2);
            powiadomieniadzien2.setText("Czas: " + hourstring + ":" + minutestring);
            time2 = hourstring + "" + minutestring;
        }
        timezmienna = "";
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String monthstring = ""+month;
        String daystring = ""+dayOfMonth;
        if(month < 10) {
            monthstring = "0" + monthstring;
        }
        if(dayOfMonth < 10) {
            daystring = "0" + daystring;
        }
        powiadomieniadzien = (TextView) findViewById(R.id.powiadomieniadzien);
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
        switch(view.getId()) {
            case R.id.checkoplaty:
                boolean zaznaczony =((CheckBox)view).isChecked();
                if(zaznaczony == true)
                {
                    Intent intent = new Intent(RegisterActivity.this, CashActivity.class);
                    intent.putExtra("Register", "RegisterActivity");
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
    private void introzobaczone() {
        SharedPreferences preferencje = getApplicationContext().getSharedPreferences("mojepreferencje",MODE_PRIVATE);
        SharedPreferences.Editor edytor = preferencje.edit();
        edytor.putBoolean("introzobaczone", true);
        edytor.commit();
    }
}
