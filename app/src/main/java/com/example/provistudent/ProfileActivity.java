package com.example.provistudent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button przyciskedytuj = findViewById(R.id.edytuj);
        Cursor cursor;
        Bazadanych bazadanych;

        bazadanych = new Bazadanych(ProfileActivity.this);
        cursor = bazadanych.odczytajtekst();

        TextView imie = (TextView) findViewById(R.id.imie);
        while(cursor.moveToNext()) {
            String zapisaneimie = cursor.getString(cursor.getColumnIndex("nazwa_uzytkownika"));
            imie.setText(zapisaneimie);
        }

        przyciskedytuj.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
