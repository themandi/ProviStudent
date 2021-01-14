package com.example.provistudent.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.provistudent.R;

public class InfoActivity extends AppCompatActivity {

    TextView tekstoaplikacji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tekstoaplikacji = findViewById(R.id.tekstoaplikacji);
        tekstoaplikacji.setText("Aplikacja ProviStudent pomaga utrzymać kontrolę nad wydatkami za pomocą codziennej możliwości wprowadzania budżetu, " +
                "oraz obserwowania statystyk z ostatnich kilku miesięcy, aby móc porównywać oszczędności oraz wydatki. Posiada możliwość ustawienia przypomnienia o wpisywaniu" +
                "wydatków, która wysyła na telefon powiadomienie o odpowiadającej nam godzinie. Program pozwala również na oszczędzanie pieniędzy, " +
                "informuje ile można dziennie wydać pieniędzy, jeśli pod koniec miesiąca pieniądze nie zostaną wydane, zostają zapisywane do 'Oszczędności'." +
                "Aplikacja posiada również opcje przypominania o opłaceniu rachunków co dany okres czasu.");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}