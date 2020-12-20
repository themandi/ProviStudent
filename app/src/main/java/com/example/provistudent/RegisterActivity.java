package com.example.provistudent;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.os.SystemClock;
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

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
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
    Calendar c;
    String timezmienna = "";
    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "kanal1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        notificationManager = NotificationManagerCompat.from(this);

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
        cursor = bazadanych.odczytajtekst();
        cursor2 = bazadanych.odczytajtekst4();
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
                Toast.makeText(getApplicationContext(), "6: "+cursor.getString(6),Toast.LENGTH_SHORT).show();

            }
            cursor.close();
        }

        if(cursor2.getCount()==0){
            Toast.makeText(getApplicationContext(), "No data",Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor2.moveToNext())
            {
                Toast.makeText(getApplicationContext(), "1: "+cursor2.getString(1),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "2: "+cursor2.getString(2),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "3: "+cursor2.getString(3),Toast.LENGTH_SHORT).show();
            }
            cursor2.close();
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
                czestotliwoscopcje = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 1:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co miesiąc", Toast.LENGTH_SHORT).show();
                        scheduleNotification(getNotification("5 second delay"), 5000);
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co trzy miesiące", Toast.LENGTH_SHORT).show();
//                        scheduleNotification(getNotification("10 second delay"), 10000);
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co pół roku", Toast.LENGTH_SHORT).show();
//                        scheduleNotification(getNotification("30 second delay"), 30000);
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Wybrano opcje: Co rok", Toast.LENGTH_SHORT).show();
//                        scheduleNotification(getNotification("60 second delay"), 60000);
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
//                dodajpowiadomienie();
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


//    private void scheduleNotification(Notification notification) {
//
//        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
//        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
//        int year = 2020;
//        int month = 12;
//        int day = 20;
//        int hour = 19;
//        int minute = 25;
//        Calendar calendar =  Calendar.getInstance();
//        calendar.set(year, month, day, hour, minute);
//        calendar.set(Calendar.SECOND, 0);
//        long kiedy = calendar.getTimeInMillis();
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
//        alarmManager.set(AlarmManager.RTC, kiedy, pendingIntent);
//    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 3);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        return builder.build();
    }

//    private void stworzkanalpowiadomienia() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel kanal = new NotificationChannel(CHANNEL_1_ID, "Kanal", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(kanal);
//        }
//    }
//
//    private void dodajpowiadomienie() {
//        String message = "blabla";
//        Intent activity = new Intent(this, MainActivity.class);
//        PendingIntent content = PendingIntent.getActivity(this,
//                0, activity, 0);
//        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
//        broadcastIntent.putExtra("Opłać wszystkie", message);
//        broadcastIntent.putExtra("czas", time);
//        broadcastIntent.putExtra("data", date);
//        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
//                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification powiadomienie = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentTitle("Zapłać za opłaty stałe!")
//                .setContentText("Kliknij i opłać opłaty stałe!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setColor(Color.GREEN)
//                .setContentIntent(content)
//                .setAutoCancel(true)
//                .setOnlyAlertOnce(true)
//                .addAction(R.mipmap.ic_launcher, "Opłać wszystkie", actionIntent)
//                .addAction(R.mipmap.ic_launcher, "Odłóż", actionIntent)
//                .build();
//        notificationManager.notify(1, powiadomienie);
//    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        String imie = poleimie.getText().toString();
        int oszczednosci = 0;
        if(wybrano2 == "Yes") {
            data = date;
            godzina2 = time2;
        }
        if(wybrano3 == "Yes") {
            oszczednosci = Integer.parseInt(poleoszczednosci.getText().toString());
        }
        if(wybrano4 == "Yes") {
            godzina = time;
        }
        if(!imie.isEmpty()) {
            if (cursor.getCount() == 0) {
                if (bazadanych.dodajtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci)) {
                    poleimie.setText("");
                    poleoszczednosci.setText("");
                }
            }
            else if (cursor.getCount() > 0) {
                if (bazadanych.zaaktualizujtekst(imie, wybrano, wybrano2, wybrano3, wybrano4, oszczednosci)) {
                    poleimie.setText("");
                    poleoszczednosci.setText("");
                }
            }
            if(wybrano2 == "Yes" & wybrano4 == "Yes") {
                if(cursor2.getCount() == 0) {
                    bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
                else {
                    bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
            }
            else if(wybrano2 == "Yes") {
                godzina = null;
                if(cursor2.getCount() == 0) {
                    bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
                else {
                    bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
            }
            else if(wybrano4 == "Yes") {
                data = null;
                godzina2 = null;
                if(cursor2.getCount() == 0) {
                    bazadanych.dodajtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
                else {
                    bazadanych.zaaktualizujtekst4(data, godzina2, czestotliwoscopcje, godzina);
                }
            }
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Nic nie zostało zapisane!",Toast.LENGTH_SHORT).show();
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
