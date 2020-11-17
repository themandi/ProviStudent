package com.example.provistudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bazadanych extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "bazadanych";
    private static final String TABLE_NAME = "Uzytkownik";
    private static final String COL1 = "ID";
    private static final String COL2 = "nazwa_uzytkownika";
    private static final String COL3 = "checkoplaty";
    private static final String COL4 = "checkoplatykiedy";
    private static final String COL5 = "checkoszczednosci";
    private static final String COL6 = "checkdane";

    private static final String TABLE_NAME2 = "Dochod";
    private static final String COL2_1 = "ID";
    private static final String COL2_2 = "zasob";
    private static final String COL2_3 = "kwota";

    public Bazadanych(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String stworztabele = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 +" TEXT, " + COL4 +" TEXT, " + COL5 +" TEXT, " + COL6 +" TEXT)";
    String stworztabele2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2_2 +" TEXT, " + COL2_3 +" TEXT)";

    db.execSQL(stworztabele);
    db.execSQL(stworztabele2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean dodajtekst(String nazwa_uzytkownika, String wybranocheckoplaty, String wybranocheckoplatykiedy, String wybranocheckoszczednosci, String wybranocheckdane){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2,nazwa_uzytkownika);
        zawartosc.put(COL3,wybranocheckoplaty);
        zawartosc.put(COL4,wybranocheckoplatykiedy);
        zawartosc.put(COL5,wybranocheckoszczednosci);
        zawartosc.put(COL6,wybranocheckdane);
        sqLitebaza.insert(TABLE_NAME, null, zawartosc);
        return true;
    }

    public boolean dodajtekst2(String zasob, String kwota){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 = new ContentValues();
        zawartosc2.put(COL2_2,zasob);
        zawartosc2.put(COL2_3,kwota);
        sqLitebaza.insert(TABLE_NAME2, null, zawartosc2);
        return true;
    }

    public boolean zaaktualizujtekst(String nazwa_uzytkownika, String wybranocheckoplaty,  String wybranocheckoplatykiedy, String wybranocheckoszczednosci, String wybranocheckdane){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2, nazwa_uzytkownika);
        zawartosc.put(COL3, wybranocheckoplaty);
        zawartosc.put(COL4,wybranocheckoplatykiedy);
        zawartosc.put(COL5,wybranocheckoszczednosci);
        zawartosc.put(COL6,wybranocheckdane);
        sqLitebaza.update(TABLE_NAME, zawartosc, COL1 + "=ID", null);
        return true;
    }

    public boolean zaaktualizujtekst2(String zasob, String kwota){
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 =  new ContentValues();
        zawartosc2.put(COL2_2,zasob);
        zawartosc2.put(COL2_3,kwota);
        sqLitebaza.update(TABLE_NAME2, zawartosc2, COL2_1 + "=ID", null);
        return true;
    }

    public Integer usuntekst2(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME2, COL2_1 + "=ID", null);
    }

    public Cursor odczytajtekst(){
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor odczytajtekst2(){
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return cursor;
    }
}