package com.example.provistudent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
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
    int sumadochodu;
    int sumawydatkow;
    int sumaoszczednosci;
    int sumadochodaktualny;
    int sumakartabankowa;
    int sumagotowka;

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
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat nazwa_miesiaca= new SimpleDateFormat("LLLL", new Locale("pl", "PL"));
        String miesiacwybrane=nazwa_miesiaca.format(cal.getTime());
        miesiac.setText(miesiacwybrane.toUpperCase());

        dochod = findViewById(R.id.dochod);
        sumadochodu = bazadanych.sumadochodu();
        dochod.setText("Dochód początkowy: " + Integer.toString(sumadochodu) + " zł");

        wydatki = findViewById(R.id.wydatki);
        sumawydatkow = bazadanych.sumawydatkow();
        wydatki.setText("Wydatki: " + Integer.toString(sumawydatkow) + " zł");

        dochodaktualny = findViewById(R.id.dochodaktualny);
        sumadochodaktualny = sumadochodu - sumawydatkow;
        dochodaktualny.setText("Dochód aktualny: " + Integer.toString(sumadochodaktualny) + " zł");

        oszczednosci = findViewById(R.id.oszczednosci);
        sumaoszczednosci = bazadanych.sumaoszczednosci();
        oszczednosci.setText("Oszczędności: " + Integer.toString(sumaoszczednosci) + " zł");

        kartabankowa = findViewById(R.id.zasob1);
        sumakartabankowa = bazadanych.sumakartabankowa();
        kartabankowa.setText("Karta bankowa: " + Integer.toString(sumakartabankowa) + " zł");

        gotowka = findViewById(R.id.zasob2);
        sumagotowka = bazadanych.sumagotowka();
        gotowka.setText("Gotówka: " + Integer.toString(sumagotowka) + " zł");

        mCalendarView = (CalendarView) findViewById(R.id.zobaczKalendarz);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String miesiac = "";
                String data = year + "/" + month + "/" + dayOfMonth;
                Log.d(TAG, "onSelectDayChange: data:" + data);
                if(month == 0) {
                    miesiac = "stycznia";
                }
                if(month == 1) {
                    miesiac = "lutego";
                }
                if(month == 2) {
                    miesiac = "marca";
                }
                if(month == 3) {
                    miesiac = "kwietnia";
                }
                if(month == 4) {
                    miesiac = "maja";
                }
                if(month == 5) {
                    miesiac = "czerwca";
                }
                if(month == 6) {
                    miesiac = "lipca";
                }
                if(month == 7) {
                    miesiac = "sierpnia";
                }
                if(month == 8) {
                    miesiac = "września";
                }
                if(month == 9) {
                    miesiac = "października";
                }
                if(month == 10) {
                    miesiac = "listopada";
                }
                if(month == 11) {
                    miesiac = "grudnia";
                }
                String dzienimiesiac = dayOfMonth + " " + miesiac;
                Log.d(TAG, "onSelectDayChange: data:" + dzienimiesiac);

                Intent intent = new Intent(MainActivity.this, DayofcalendarActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("data2",dzienimiesiac);
                startActivity(intent);
            }
        });

        cheatday = (Button) findViewById(R.id.cheatday);
        cheatday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheatdayActivity.class);
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
        listawykres.add(new PieEntry(sumawydatkow, "Wydatki"));
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
                    finish();
                    System.exit(0);
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
}