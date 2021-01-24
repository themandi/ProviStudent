package com.example.provistudent.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.provistudent.Adapters.IntroViewPagerAdapter;
import com.example.provistudent.R;
import com.example.provistudent.Helpers.ScreenItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenpager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    TextView przyciskpomin;
    Button przyciskdalej;
    Button przyciskrozpocznij;
    Animation przyciskanimacji;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(introuzyskaj()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        przyciskdalej = findViewById(R.id.dalej);
        tabIndicator = findViewById(R.id.tab_indicator);
        przyciskrozpocznij = findViewById(R.id.rozpocznij);
        przyciskpomin = findViewById(R.id.pomin);
        przyciskanimacji = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.przyciskanimacji);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Kontroluj wydatki", "Kontroluj wydatki razem z nami! Funkcjonalność aplikacji pozwala na wprowadzenie budżetu oraz obserwowanie statystyk. Aplikacja po zaznaczeniu opcji przypomnienia o wpisywaniu wydatków wysyła komunikat o odpowiadającej nam godzinie. ", R.drawable.image2));
        mList.add(new ScreenItem("Oszczędzaj", "Oszczędzanie nigdy nie było tak proste! Aplikacja oblicza dzienną kwotę którą możesz wydać, natomiast odłożone pieniądze pojawiają się w rubryce 'Oszczędności'", R.drawable.image3));
        mList.add(new ScreenItem("Pamiętaj o rachunkach", "Pamiętanie o opłacaniu rachunkach bywa zwykle uciążliwe. System powiadomień pozwala na wysyłanie co określony okres czasu komunikat przypominający o opłaceniu rachunków.", R.drawable.image1));

        screenpager = findViewById(R.id.screenpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenpager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenpager);

        przyciskdalej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenpager.getCurrentItem();
                if(position < mList.size()) {
                    position++;
                    screenpager.setCurrentItem(position);
                }
                if(position == mList.size()-1) {
                    zaladujostatniekran();
                }
            }
        });

        przyciskpomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            screenpager.setCurrentItem(mList.size());
            }
        });

        przyciskrozpocznij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1) {
                    zaladujostatniekran();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private boolean introuzyskaj() {
        SharedPreferences preferencje = getApplicationContext().getSharedPreferences("mojepreferencje",MODE_PRIVATE);
        Boolean introotwarte = preferencje.getBoolean("introzobaczone", false);
        return introotwarte;
    }

    private void zaladujostatniekran() {

        przyciskdalej.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        przyciskpomin.setVisibility(View.INVISIBLE);
        przyciskrozpocznij.setVisibility(View.VISIBLE);
        //ustawienia animacji
        przyciskrozpocznij.setAnimation(przyciskanimacji);
    }
}
