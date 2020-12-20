package com.example.provistudent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//HALO AGNIESZKA PISZE DO CIEBIE Z PRZESZLOSCI ZAPISZ SOBIE WYDATKI STAŁE W ZMIENNEJ (W TABLICY NA RAZIE ZAPISUJESZ INO TO, A POTEM WEŹ TO OGARNIJ pozdrawiam lofki

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    PieChart wykreskolowy;
    Bazadanych bazadanych;
    Button cheatday;
    TextView miesiac;
    TextView dochod;
    TextView dochodaktualny;
    TextView wydatki;
    TextView oszczednosci;
    TextView kartabankowa;
    TextView gotowka;
    TextView zobaczwydatki;
    TextView wydajoszczednosci;
    Cursor cursor;
    int sumadochodu;
    int sumawydatkowstalych;
    int sumaoszczednosci;
    int sumadochodaktualny;
    int sumakartabankowa;
    int sumagotowka;
    int data_aktualnaint;
    int sumawydatkow;
    String data;
    String datadozapisu;
    String dzienimiesiac;

    private DrawerLayout drawer;
    private static final String TAG = "MainActivity";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bazadanych = new Bazadanych(MainActivity.this);

        miesiac = findViewById(R.id.miesiac);
        Calendar cal = Calendar.getInstance();
        sumadochodu = bazadanych.sumadochodu();
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        String data_aktualnastring =  data_aktualna.format(cal.getTime());
        data_aktualnaint = Integer.parseInt(data_aktualnastring);
        SimpleDateFormat miesiac_aktualny = new SimpleDateFormat("MM", new Locale("pl", "PL"));
        String miesiac_aktualnyint = miesiac_aktualny.format(cal.getTime());
        SimpleDateFormat dzien_aktualny = new SimpleDateFormat("dd", new Locale("pl", "PL"));
        String dzien_aktualnystring = dzien_aktualny.format(cal.getTime());
        int dzien_aktualnyint = Integer.parseInt(dzien_aktualnystring);
        SimpleDateFormat nazwa_miesiaca= new SimpleDateFormat("LLLL", new Locale("pl", "PL"));
        String miesiacwybrane=nazwa_miesiaca.format(cal.getTime());
        miesiac.setText(miesiacwybrane.toUpperCase());
        if(dzien_aktualnyint == 01) {
            sumadochodu = bazadanych.sumadochodu();
            sumawydatkow = bazadanych.sumawydatkow();
            sumaoszczednosci = bazadanych.sumaoszczednosci();
            bazadanych.dodajtekst6(miesiac_aktualnyint, sumadochodu, sumawydatkow, sumaoszczednosci);
            bazadanych.usunwszystko5();
            cursor = bazadanych.odczytajtekst();
            int oszczednosci = (sumadochodu-sumawydatkow)+sumaoszczednosci;
            while(cursor.moveToNext())
            {
                bazadanych.zaaktualizujtekst(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), oszczednosci);
            }
            cursor.close();
        }
        cursor = bazadanych.odczytajtekst6();
        while(cursor.moveToNext())
        {
            Toast.makeText(getApplicationContext(), "1: "+cursor.getString(1),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "2: "+cursor.getString(2),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "3: "+cursor.getString(3),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "4: "+cursor.getString(4),Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        dochod = findViewById(R.id.dochod);
        findViewById(R.id.dochod).invalidate();
        sumadochodu = bazadanych.sumadochodu();
        dochod.setText("Dochód początkowy: " + Integer.toString(sumadochodu) + " zł");

        wydatki = findViewById(R.id.wydatki);
        findViewById(R.id.wydatki).invalidate();
        sumawydatkow = bazadanych.sumawydatkow();
        wydatki.setText("Wydatki: " + Integer.toString(sumawydatkow) + " zł");

        dochodaktualny = findViewById(R.id.dochodaktualny);
        findViewById(R.id.dochodaktualny).invalidate();
        sumadochodaktualny = sumadochodu - sumawydatkow;
        dochodaktualny.setText("Dochód aktualny: " + Integer.toString(sumadochodaktualny) + " zł");

        oszczednosci = findViewById(R.id.oszczednosci);
        findViewById(R.id.oszczednosci).invalidate();
        sumaoszczednosci = bazadanych.sumaoszczednosci();
        oszczednosci.setText("Oszczędności: " + Integer.toString(sumaoszczednosci) + " zł");

        kartabankowa = findViewById(R.id.zasob1);
        findViewById(R.id.zasob1).invalidate();
        sumakartabankowa = bazadanych.sumakartabankowa();
        kartabankowa.setText("Karta bankowa: " + Integer.toString(sumakartabankowa) + " zł");

        gotowka = findViewById(R.id.zasob2);
        findViewById(R.id.zasob2).invalidate();
        sumagotowka = bazadanych.sumagotowka();
        gotowka.setText("Gotówka: " + Integer.toString(sumagotowka) + " zł");

        try {
            sprawdzdata();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        zobaczwydatki = (TextView) findViewById(R.id.zobaczwydatki);
        zobaczwydatki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cursor = bazadanych.odczytajtekst5();
                if (cursor.getCount() == 0) {
                    wyswietlwiadomosc2("Error", "Brak zapisanych zasobów!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID: " + cursor.getString(0) + "\n");
                    buffer.append("Data: " + cursor.getString(1) + "\n");
                    buffer.append("Wydatek: " + cursor.getString(2) + "\n");
                    buffer.append("Kwota: " + cursor.getString(3) + "\n");
                }
                wyswietlwiadomosc2("Zapisane wydatki: ", buffer.toString());
                cursor.close();
            }
        });

        wydajoszczednosci = (TextView) findViewById(R.id.wydajoszczednosci);
        wydajoszczednosci.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cursor = bazadanych.odczytajtekst();
                StringBuffer buffer = new StringBuffer();
                    buffer.append("Posiadasz: " + sumaoszczednosci + " zł oszczędności"+ "\n");
                    buffer.append("Czy wykorzystać swoje oszczędności?" + "\n");
                wyswietlwiadomosc("Wykorzystaj oszczędności", buffer.toString());
            }
        });

        mCalendarView = (CalendarView) findViewById(R.id.zobaczKalendarz);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String monthstring = ""+month;
                String daystring = ""+dayOfMonth;
                if(month < 10) {
                    monthstring = "0" + monthstring;
                }
                if(dayOfMonth < 10) {
                    daystring = "0" + daystring;
                }
                data = daystring + "/" + monthstring + "/" + year;
                datadozapisu = year + "" + monthstring + "" + daystring;
                Log.d(TAG, "onSelectDayChange: data:" + data);

                String miesiac = "";
                if(month == 1) {
                    miesiac = "stycznia";
                }
                if(month == 2) {
                    miesiac = "lutego";
                }
                if(month == 3) {
                    miesiac = "marca";
                }
                if(month == 4) {
                    miesiac = "kwietnia";
                }
                if(month == 5) {
                    miesiac = "maja";
                }
                if(month == 6) {
                    miesiac = "czerwca";
                }
                if(month == 7) {
                    miesiac = "lipca";
                }
                if(month == 8) {
                    miesiac = "sierpnia";
                }
                if(month == 9) {
                    miesiac = "września";
                }
                if(month == 10) {
                    miesiac = "października";
                }
                if(month == 11) {
                    miesiac = "listopada";
                }
                if(month == 12) {
                    miesiac = "grudnia";
                }
                dzienimiesiac = daystring + " " + miesiac;
                Log.d(TAG, "onSelectDayChange: data:" + dzienimiesiac);

                Intent intent = new Intent(MainActivity.this, DayofcalendarActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("data2",dzienimiesiac);
                intent.putExtra("datadozapisu",datadozapisu);
                startActivity(intent);
            }
        });

        cheatday = (Button) findViewById(R.id.cheatday);
        cheatday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheatdayActivity.class);
                intent.putExtra("Main", "MainActivity");
                startActivity(intent);
            }
        });

        wykreskolowy = (PieChart) findViewById(R.id.wykreskolowy);
        wykreskolowy.setNoDataText("");
        wykreskolowy.setUsePercentValues(true);
        wykreskolowy.setRotationEnabled(true);
        wykreskolowy.getDescription().setEnabled(false);
        wykreskolowy.setExtraOffsets(5, 10, 5, 5);
        wykreskolowy.setDragDecelerationFrictionCoef(0.95f);
        wykreskolowy.setDrawHoleEnabled(false);
        wykreskolowy.setDrawEntryLabels(false);

        ArrayList<PieEntry> listawykres = new ArrayList<>();
        listawykres.add(new PieEntry(sumadochodu, "Dochód"));
        listawykres.add(new PieEntry(sumawydatkowstalych, "Wydatki"));
        listawykres.add(new PieEntry(sumaoszczednosci, "Oszczędności"));

        PieDataSet dataSet = new PieDataSet(listawykres, "");
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(3, 215, 252), Color.rgb(252, 3, 73), Color.rgb(3, 252, 132));

        PieData data = new PieData((dataSet));
        data.setValueFormatter(new PercentFormatter(wykreskolowy));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        wykreskolowy.setData(data);
        wykreskolowy.invalidate();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView uzytkowniknazwa = (TextView) headerView.findViewById(R.id.uzytkowniknazwa);
        cursor = bazadanych.odczytajtekst();
        while(cursor.moveToNext()) {
            String zapisaneimie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
            uzytkowniknazwa.setText("Witaj, " + zapisaneimie + "!");
        }
        cursor.close();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void sprawdzdata() throws ParseException {
        int maxid = bazadanych.odczytajmaxdate();
        Calendar cal = Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int kwotawydaj = sumadochodu/days;
        String wydatek = "Automatyczny";
        String cheatday = "No";
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        String data_aktualnastring =  data_aktualna.format(cal.getTime());
        int data_aktualnaint = Integer.parseInt(data_aktualnastring);
        if(data_aktualnaint > maxid) {
            bazadanych.dodajtekst5(data_aktualnaint, wydatek, kwotawydaj, cheatday);
            zwiekszdate(data_aktualnaint);
        }
    }

    private int zwiekszdate(int data_aktualnaint) throws ParseException {
        SimpleDateFormat data_aktualna = new SimpleDateFormat("yyyyMMdd", new Locale("pl", "PL"));
        Calendar cal = Calendar.getInstance();
        String data_aktualnastring =  data_aktualna.format(cal.getTime());
        cal.setTime(data_aktualna.parse(data_aktualnastring));
        cal.add(Calendar.DATE, 1);
        String zmiendate=data_aktualna.format(cal.getTime());
        int zmianadatyint = Integer.parseInt(zmiendate);
        data_aktualnaint = zmianadatyint;
        return data_aktualnaint;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_stats:
                Intent intent1 = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_settings:
                Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_help:
                Intent intent3 = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_info:
                Intent intent4 = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_exit:
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                System.exit(1);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void wyswietlwiadomosc2(String tytul, String wiadomosc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.show();
    }
    public void wyswietlwiadomosc(String tytul, String wiadomosc){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.setNegativeButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int kwota = bazadanych.sumaoszczednosci();
                if (kwota != 0) {
                    String cheatday = "No";
                    String wydatek = "Oszczędności";
                    if (bazadanych.dodajtekst5(data_aktualnaint, wydatek, kwota, cheatday)) {
                        cursor = bazadanych.odczytajtekst();
                        int oszczednosci = 0;
                        while(cursor.moveToNext())
                        {
                            bazadanych.zaaktualizujtekst(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), oszczednosci);
                        }
                        cursor.close();
                    }
                    Toast.makeText(getApplicationContext(), "Wydano oszczędności!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nie masz do wykorzystania oszczędności!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNeutralButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}