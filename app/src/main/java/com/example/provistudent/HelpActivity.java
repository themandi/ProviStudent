package com.example.provistudent;

import android.app.AlertDialog;
import android.os.Bundle;

import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button1;
        Button button2;
        Button button3;
        Button button4;
        Button button5;
        Button button6;
        Button button7;

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                    buffer.append("Aby zacząć używać poprawnie aplikacji, należy wprowadzić wszystkie dane zgodnie z prawdą," +
                                    " jeśli zostały one wprowadzone nieprawidłowo należy je edytować wchodząc w Menu i klikając opcje 'Ustawienia'." +
                            " Następnie należy wprowadzać codziennie dane dotyczące wydatków i je samodzielnie kontrolować.");
                wyswietlwiadomosc("Jak używać aplikację?", buffer.toString());
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Aby sprawdzić swoje dane wprowadzone przy rejestracji należy wejść w zakładkę Menu i wybrać opcje 'Profil'");
                wyswietlwiadomosc("Gdzie sprawdzić swoje dane?", buffer.toString());
            }
        });
        button3 = findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Aby sprawdzić statystyki naszych wydatków, należy wejść w zakładkę Menu i wybrać opcje 'Statystyki'");
                wyswietlwiadomosc("Jak sprawdzić statystyki?", buffer.toString());
            }
        });
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Aby wprowadzić wydatki należy wejść w dany dzień w kalendarzu dla którego chcemy wprowadzić wydatki i użyć opcji 'Edytuj wydatki'. " +
                                "Jeśli nasze wydatki nie zostaną przez Ciebie wprowadzone system zapisuje sugerowaną ilość do wydania na jeden dzień. " +
                                "Jeśli użyjesz opcji 'Edytuj wydatki' twoje wydatki zostaną zaaktualizowane i wprowadzone poprawne do systemu, a poprzednie" +
                        " zostaną usunięte");
                wyswietlwiadomosc("Jak wprowadzić wydatki?", buffer.toString());
            }
        });
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Funkcja 'Cheatday' służy do zapisania wydatków, które zostały wprowadzone nadprogramowo, spowodowane nagłymi, niespodziewanymi " +
                                "wydatkami, takimi jak kupno nowych ubrań, impreza, popsuty samochód itp.");
                wyswietlwiadomosc("Co to jest 'Cheatday'?", buffer.toString());

            }
        });
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Oszczędności są to pieniądze odłożone w ciągu całego miesiąca. Na początku rejestracji wprowadzamy odłożone już oszczędności, " +
                        "następnie jeśli w danym miesiącu nie zostanie wykorzystany cały dochód, pieniądze które zostały pod koniec miesiąca są odkładane " +
                        "do naszych oszczędności. Oszczędności można wydać klikając przycisk 'Wydaj oszczędności' w głównej aktywności.");
                wyswietlwiadomosc("Jak działa system 'Oszczędności'", buffer.toString());
            }
        });
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("Aby edytować dane, należy wejść w zakładkę Menu i wybrać opcje 'Ustawienia'");
                wyswietlwiadomosc("Jak edytować dane?", buffer.toString());

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    public void wyswietlwiadomosc(String tytul, String wiadomosc){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tytul);
        builder.setMessage(wiadomosc);
        builder.show();
    }
}