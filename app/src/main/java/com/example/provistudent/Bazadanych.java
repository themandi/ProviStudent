package com.example.provistudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Bazadanych extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bazadanych";
    private static final String TABLE_NAME = "Uzytkownik";
    private static final String COL1 = "ID";
    private static final String COL2 = "nazwa_uzytkownika";
    private static final String COL3 = "checkoplaty";
    private static final String COL4 = "checkoplatykiedy";
    private static final String COL5 = "checkoszczednosci";
    private static final String COL6 = "checkdane";
    private static final String COL7 = "oszczednosci";

    private static final String TABLE_NAME2 = "Dochod";
    private static final String COL2_1 = "ID";
    private static final String COL2_2 = "zasob";
    private static final String COL2_3 = "kwota";

    private static final String TABLE_NAME3 = "Wydatki_stale";
    private static final String COL3_1 = "ID";
    private static final String COL3_2 = "wydatek";
    private static final String COL3_3 = "kwota";

    private static final String TABLE_NAME4 = "Powiadomienia";
    private static final String COL4_1 = "ID";
    private static final String COL4_2 = "kiedypow";
    private static final String COL4_3 = "czestotliwosc";
    private static final String COL4_4 = "kiedydane";

    private static final String TABLE_NAME5 = "Wydatki";
    private static final String COL5_1 = "ID";
    private static final String COL5_2 = "wydatek";
    private static final String COL5_3 = "kwota";

    public Bazadanych(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String stworztabele = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " INTEGER)";
        String stworztabele2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2_2 + " TEXT, " + COL2_3 + " INTEGER)";
        String stworztabele3 = "CREATE TABLE " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL3_2 + " TEXT, " + COL3_3 + " INTEGER)";
        String stworztabele4 = "CREATE TABLE " + TABLE_NAME4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL4_2 + " TEXT, " + COL4_3 + " TEXT, " + COL4_4 + " TEXT)";
        String stworztabele5 = "CREATE TABLE " + TABLE_NAME5 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL5_2 + " TEXT, " + COL5_3 + " INTEGER)";

        db.execSQL(stworztabele);
        db.execSQL(stworztabele2);
        db.execSQL(stworztabele3);
        db.execSQL(stworztabele4);
        db.execSQL(stworztabele5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }

    public boolean dodajtekst(String nazwa_uzytkownika, String wybranocheckoplaty, String wybranocheckoplatykiedy, String wybranocheckoszczednosci, String wybranocheckdane, Integer oszczednosci) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2, nazwa_uzytkownika);
        zawartosc.put(COL3, wybranocheckoplaty);
        zawartosc.put(COL4, wybranocheckoplatykiedy);
        zawartosc.put(COL5, wybranocheckoszczednosci);
        zawartosc.put(COL6, wybranocheckdane);
        zawartosc.put(COL7, oszczednosci);
        sqLitebaza.insert(TABLE_NAME, null, zawartosc);
        return true;
    }

    public boolean dodajtekst2(String zasob, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 = new ContentValues();
        zawartosc2.put(COL2_2, zasob);
        zawartosc2.put(COL2_3, kwota);
        sqLitebaza.insert(TABLE_NAME2, null, zawartosc2);
        return true;
    }

    public boolean dodajtekst3(String wydatek, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc3 = new ContentValues();
        zawartosc3.put(COL3_2, wydatek);
        zawartosc3.put(COL3_3, kwota);
        sqLitebaza.insert(TABLE_NAME3, null, zawartosc3);
        return true;
    }

    public boolean dodajtekst4(String kiedypow, String czestotliwosc, String kiedydane) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc4 = new ContentValues();
        zawartosc4.put(COL4_2, kiedypow);
        zawartosc4.put(COL4_3, czestotliwosc);
        zawartosc4.put(COL4_4, kiedydane);
        sqLitebaza.insert(TABLE_NAME4, null, zawartosc4);
        return true;
    }
    public boolean dodajtekst5(String wydatek, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc5 = new ContentValues();
        zawartosc5.put(COL5_2, wydatek);
        zawartosc5.put(COL5_3, kwota);
        sqLitebaza.insert(TABLE_NAME5, null, zawartosc5);
        return true;
    }

    public boolean zaaktualizujtekst(String nazwa_uzytkownika, String wybranocheckoplaty, String wybranocheckoplatykiedy, String wybranocheckoszczednosci, String wybranocheckdane, Integer oszczednosci) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(COL2, nazwa_uzytkownika);
        zawartosc.put(COL3, wybranocheckoplaty);
        zawartosc.put(COL4, wybranocheckoplatykiedy);
        zawartosc.put(COL5, wybranocheckoszczednosci);
        zawartosc.put(COL6, wybranocheckdane);
        zawartosc.put(COL7, oszczednosci);
        sqLitebaza.update(TABLE_NAME, zawartosc, COL1 + "=ID", null);
        return true;
    }

    public boolean zaaktualizujtekst2(String zasob, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 = new ContentValues();
        zawartosc2.put(COL2_2, zasob);
        zawartosc2.put(COL2_3, kwota);
        sqLitebaza.update(TABLE_NAME2, zawartosc2, COL2_1 + "=ID", null);
        return true;
    }

    public boolean zaaktualizujtekst3(String wydatek, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc3 = new ContentValues();
        zawartosc3.put(COL3_2, wydatek);
        zawartosc3.put(COL3_3, kwota);
        sqLitebaza.update(TABLE_NAME3, zawartosc3, COL3_1 + "=ID", null);
        return true;
    }

    public boolean zaaktualizujtekst4(String kiedypow, String czestotliwosc, String kiedydane) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc4 = new ContentValues();
        zawartosc4.put(COL4_2, kiedypow);
        zawartosc4.put(COL4_3, czestotliwosc);
        zawartosc4.put(COL4_4, kiedydane);
        sqLitebaza.update(TABLE_NAME4, zawartosc4, COL4_1 + "=ID", null);
        return true;
    }
    public boolean zaaktualizujtekst5(String wydatek, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc5 = new ContentValues();
        zawartosc5.put(COL5_2, wydatek);
        zawartosc5.put(COL5_3, kwota);
        sqLitebaza.update(TABLE_NAME3, zawartosc5, COL5_1 + "=ID", null);
        return true;
    }

    public Integer usuntekst2(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME2, COL2_1 + "=ID", null);
    }

    public Integer usuntekst3(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME3, COL3_1 + "=ID", null);
    }

    public Integer usuntekst5(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME5, COL5_1 + "=ID", null);
    }

    public Cursor odczytajtekst() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor odczytajtekst2() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return cursor;
    }

    public Cursor odczytajtekst3() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return cursor;
    }

    public Cursor odczytajtekst4() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME4, null);
        return cursor;
    }

    public Cursor odczytajtekst5() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME5, null);
        return cursor;
    }

    public int sumadochodu() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM " + TABLE_NAME2, null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumawydatkow() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL3_3 + ") FROM " + TABLE_NAME3, null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumaoszczednosci() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL7 + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumakartabankowa() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM Dochod WHERE zasob = 'Karta płatnicza'", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumagotowka() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM Dochod WHERE zasob = 'Gotówka'", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }
    public int kwotawydana() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT kwota FROM Wydatki", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }
}