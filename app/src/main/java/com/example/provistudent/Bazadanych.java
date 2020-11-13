package com.example.provistudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bazadanych extends SQLiteOpenHelper{
    private static final String TAG = "Bazadanych";
    private static final String TABLE_NAME = "Uzytkownik";
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

    public boolean dodajtekst(String nazwa_uzytkownika){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2,nazwa_uzytkownika);
        sqLitebaza.insert("Uzytkownik", null, zawartosc);
        return true;
    }
    public boolean zaaktualizujtekst(String nazwa_uzytkownika){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2, nazwa_uzytkownika);
        sqLitebaza.update("Uzytkownik", zawartosc, COL1 + "=ID", null);
        return true;
    }
    public Cursor odczytajtekst(){
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("select * from  Uzytkownik", null);
        return cursor;
    }
}
