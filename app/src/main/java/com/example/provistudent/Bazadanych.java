package com.example.provistudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Bazadanych extends SQLiteOpenHelper{
    private static final String TAG = "Bazadanych";
    private static final String TABLE_NAME = "uzytkownicy";
    private static final String COL1 = "ID";
    private static final String COL2 = "nazwa_uzytkownika";

    public Bazadanych(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String stworztabele = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT)";
    db.execSQL(stworztabele);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean dodajtekst(String text){
        //uzyskujemy zapisywalną baze danych
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        //tworzymy wartości treści
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2,text);
        //dodajemy wartości do bazy
        sqLitebaza.insert(TABLE_NAME, null, zawartosc);
        return true;
    }
    public ArrayList odczytajtekst(){
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            lista.add(cursor.getString(cursor.getColumnIndex(COL2)));
            cursor.moveToNext();
        }
        return lista;
    }
}
