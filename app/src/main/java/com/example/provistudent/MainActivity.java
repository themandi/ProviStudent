package com.example.provistudent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CalendarView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PieChart wykreskolowy;
    float dochod = 1234;
    float wydatki = 950;
    float oszczednosci = 500;

    private static final String TAG = "MainActivity";
    private CalendarView mCalendarView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mCalendarView = (CalendarView) findViewById(R.id.zobaczKalendarz);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = year + "/" + month + "/" + dayOfMonth;
                Log.d(TAG, "onSelectDayChange: data:" + data);
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
        listawykres.add(new PieEntry(dochod, "Dochód"));
        listawykres.add(new PieEntry(wydatki, "Wydatki"));
        listawykres.add(new PieEntry(oszczednosci, "Oszczędności"));

        PieDataSet dataSet = new PieDataSet(listawykres, "");
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(3, 215, 252), Color.rgb(252, 3, 73), Color.rgb(3, 252, 132));

        PieData data = new PieData((dataSet));
        data.setValueFormatter(new PercentFormatter(wykreskolowy));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        wykreskolowy.setData(data);
        wykreskolowy.invalidate();

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
