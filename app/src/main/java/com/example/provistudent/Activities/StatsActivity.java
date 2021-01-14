package com.example.provistudent.Activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.provistudent.Database.Bazadanych;
import com.example.provistudent.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {
    BarChart wykres;
    Cursor cursor;
    Bazadanych bazadanych;
    int liczbagrup = 0;
    int rokint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat rok = new SimpleDateFormat("yyyy", new Locale("pl", "PL"));
        String rokstring =  rok.format(cal.getTime());
        rokint = Integer.parseInt(rokstring);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bazadanych = new Bazadanych(StatsActivity.this);

        wykres = findViewById(R.id.wykres);

        ArrayList<BarEntry> listaprzychodow = new ArrayList<>();
        cursor = bazadanych.odczytajtekst6();
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String przychod = cursor.getString(cursor.getColumnIndex("przychod"));
                int przychodint = Integer.parseInt(przychod);
                int x = 1;
                listaprzychodow.add(new BarEntry(x, przychodint));
                x++;
            }
            cursor.close();
        }

        ArrayList<BarEntry> listawydatkow = new ArrayList<>();
        cursor = bazadanych.odczytajtekst6();
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String wydatki = cursor.getString(cursor.getColumnIndex("wydatek"));
                int wydatkiint = Integer.parseInt(wydatki);
                int x = 1;
                listawydatkow.add(new BarEntry(x, wydatkiint));
                x++;
            }
            cursor.close();
        }

        ArrayList<BarEntry> listaoszczednosci = new ArrayList<>();
        cursor = bazadanych.odczytajtekst6();
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String oszczednosci = cursor.getString(cursor.getColumnIndex("oszczednosci"));
                int oszczednosciint = Integer.parseInt(oszczednosci);
                int x = 1;
                listaoszczednosci.add(new BarEntry(x, oszczednosciint));
                x++;
            }
            cursor.close();
        }

        BarDataSet barDataSet1 = new BarDataSet(listaprzychodow,"Przychód");
        barDataSet1.setColor(Color.BLUE);
        BarDataSet barDataSet2 = new BarDataSet(listawydatkow,"Wydatki");
        barDataSet2.setColor(Color.RED);
        BarDataSet barDataSet3 = new BarDataSet(listaoszczednosci,"Oszczędności");
        barDataSet3.setColor(Color.GREEN);

        BarData data = new BarData(barDataSet1,barDataSet2,barDataSet3);
        wykres.setData(data);
        ArrayList<String> listamiesiecy = new ArrayList<String>();
        cursor = bazadanych.odczytajtekst6();
        if(cursor != null && cursor.getCount() > 0) {
           while(cursor.moveToNext()) {
                String miesiac = cursor.getString(cursor.getColumnIndex("miesiac"));
                if(miesiac.equals("01"))
                {
                    liczbagrup++;
                    listamiesiecy.add("Styczeń");
                }
               if(miesiac.equals("02"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Luty");
               }
               if(miesiac.equals("03"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Marzec");
               }
               if(miesiac.equals("04"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Kwiecień");
               }
               if(miesiac.equals("05"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Maj");
               }
               if(miesiac.equals("06"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Czerwiec");
               }
               if(miesiac.equals("07"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Lipiec");
               }
               if(miesiac.equals("08"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Sierpień");
               }
               if(miesiac.equals("09"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Wrzesień");
               }
               if(miesiac.equals("10"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Październik");
               }
               if(miesiac.equals("11"))
               {
                   liczbagrup++;
                   listamiesiecy.add("Listopad");
               }
                if(miesiac.equals("12"))
                {
                    liczbagrup++;
                    listamiesiecy.add("Grudzień");
                }
           }
            cursor.close();
        }
        XAxis xaxis = wykres.getXAxis();
        xaxis.setValueFormatter(new IndexAxisValueFormatter(listamiesiecy));
        xaxis.setCenterAxisLabels(true);
        xaxis.setPosition(XAxis.XAxisPosition.TOP);
        xaxis.setGranularity(1);
        xaxis.setGranularityEnabled(true);

        wykres.setDragEnabled(true);
//        wykres.setVisibleXRangeMinimum(3);
        wykres.getDescription().setText("Rok: " + rokint);
//        wykres.getDescription().setEnabled(false);

        float przestrzenwykresu = 0.07f;
        float grupaprzestrzeni = 0.19f;
        data.setBarWidth(0.20f);

        wykres.getXAxis().setAxisMinimum(0);
        wykres.getXAxis().setAxisMaximum(0+wykres.getBarData().getGroupWidth(grupaprzestrzeni,przestrzenwykresu)*liczbagrup);
        wykres.groupBars(0, grupaprzestrzeni, przestrzenwykresu);
        wykres.invalidate();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}