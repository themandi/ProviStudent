package com.example.provistudent;

import android.app.TimePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener {
    EditText poleimie;
    Button przyciskzapisz;
    Button przypominaczgodzina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Spinner wykorzystywany podczas pierwszej rejestracji użytkownika w Dochodzie
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.srodki, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        poleimie = (EditText) findViewById(R.id.poleimie);

        przyciskzapisz = (Button) findViewById(R.id.zapisz);
        przyciskzapisz.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        onZapisz();
                    }
                }
        );

        przypominaczgodzina = (Button) findViewById(R.id.przypominaczgodzina);
        przypominaczgodzina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    //Metoda wykorzystywana podczas wywołania przycisku "Zapisz"
    void onZapisz() {
        String imie = poleimie.getText().toString();
        System.out.println(imie);
    }

    @Override
//    Halo, Agnieszka to jest miejsce JAK WYBIERZESZ TĄ RZECZ. TUTAJ BĘDZIESZ ZMIENIAĆ ŻEBY WYŚWIETLAŁ SIE INNY CONTENT
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String zaznaczonesrodki = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), zaznaczonesrodki, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

    }
}
