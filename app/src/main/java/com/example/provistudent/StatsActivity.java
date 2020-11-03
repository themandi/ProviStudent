package com.example.provistudent;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    BarChart wykres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        wykres = findViewById(R.id.wykres);

            List<BarEntry> dochod = new ArrayList<>();
            dochod.add(new BarEntry(1, 2000));
            dochod.add(new BarEntry(2, 200));
            dochod.add(new BarEntry(3, 2400));
            dochod.add(new BarEntry(4, 2700));
            dochod.add(new BarEntry(5, 4000));
            dochod.add(new BarEntry(6, 3000));
            dochod.add(new BarEntry(7, 1000));

        List<BarEntry> wydatki = new ArrayList<>();
        wydatki.add(new BarEntry(1, 2000));
        wydatki.add(new BarEntry(2, 200));
        wydatki.add(new BarEntry(3, 2400));
        wydatki.add(new BarEntry(4, 2700));
        wydatki.add(new BarEntry(5, 4000));
        wydatki.add(new BarEntry(6, 3000));
        wydatki.add(new BarEntry(7, 1000));

        List<BarEntry> oszczednosci = new ArrayList<>();
        oszczednosci.add(new BarEntry(1, 2000));
        oszczednosci.add(new BarEntry(2, 200));
        oszczednosci.add(new BarEntry(3, 2400));
        oszczednosci.add(new BarEntry(4, 2700));
        oszczednosci.add(new BarEntry(5, 4000));
        oszczednosci.add(new BarEntry(6, 3000));
        oszczednosci.add(new BarEntry(7, 1000));

        BarDataSet barDataSet1 = new BarDataSet(dochod,"Dochód");
        barDataSet1.setColor(Color.GREEN);
        BarDataSet barDataSet2 = new BarDataSet(wydatki,"Wydatki");
        barDataSet2.setColor(Color.RED);
        BarDataSet barDataSet3 = new BarDataSet(oszczednosci,"Oszczędności");
        barDataSet3.setColor(Color.BLUE);

        BarData data = new BarData(barDataSet1,barDataSet2,barDataSet3);
        wykres.setData(data);
//Dopisz później wszystkie i stwórz pętle żeby odczytywało które
//        https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/BarChartActivityMultiDataset.java
        String[] months = new String[]{"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec"};
        XAxis xaxis = wykres.getXAxis();
        xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xaxis.setCenterAxisLabels(true);
        xaxis.setPosition(XAxis.XAxisPosition.TOP);
        xaxis.setGranularity(1);
        xaxis.setGranularityEnabled(true);

        wykres.setDragEnabled(true);
        wykres.setVisibleXRangeMinimum(3);
        wykres.getDescription().setEnabled(false);

        float przestrzenwykresu = 0.01f;
        float grupaprzestrzeni = 0.25f;
        data.setBarWidth(0.24f);

        wykres.getXAxis().setAxisMinimum(0);
        wykres.getXAxis().setAxisMaximum(0+wykres.getBarData().getGroupWidth(grupaprzestrzeni,przestrzenwykresu)*7);
        wykres.groupBars(0, grupaprzestrzeni, przestrzenwykresu);
        wykres.invalidate();
    }
}