package com.example.provistudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String TABLE_NAME2 = "Przychod";
    private static final String COL2_1 = "ID";
    private static final String COL2_2 = "zasob";
    private static final String COL2_3 = "kwota";
    private static final String COL2_4 = "nazwaprzychodu";

    private static final String TABLE_NAME3 = "Wydatki_stale";
    private static final String COL3_1 = "ID";
    private static final String COL3_2 = "wydatek";
    private static final String COL3_3 = "kwota";

    private static final String TABLE_NAME4 = "Powiadomienia";
    private static final String COL4_1 = "ID";
    private static final String COL4_2 = "kiedypow";
    private static final String COL4_3 = "kiedypowczas";
    private static final String COL4_4 = "czestotliwosc";
    private static final String COL4_5 = "kiedydane";
    private static final String COL4_6 = "czywlaczone";
    private static final String COL4_7 = "interwal";
    private static final String COL4_8 = "czywlaczone2";

    private static final String TABLE_NAME5 = "Wydatki";
    private static final String COL5_1 = "ID";
    private static final String COL5_2 = "data";
    private static final String COL5_3 = "wydatek";
    private static final String COL5_4 = "kwota";
    private static final String COL5_5 = "cheatday";

    private static final String TABLE_NAME6 = "Statystyki";
    private static final String COL6_1 = "ID";
    private static final String COL6_2 = "miesiac";
    private static final String COL6_3 = "przychod";
    private static final String COL6_4 = "wydatek";
    private static final String COL6_5 = "oszczednosci";

    public Bazadanych(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String stworztabele = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " INTEGER)";
        String stworztabele2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2_2 + " TEXT, " + COL2_3 + " INTEGER, " + COL2_4 + " TEXT)";
        String stworztabele3 = "CREATE TABLE " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL3_2 + " TEXT, " + COL3_3 + " INTEGER)";
        String stworztabele4 = "CREATE TABLE " + TABLE_NAME4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL4_2 + " TEXT, " + COL4_3 + " TEXT, " + COL4_4 + " TEXT, " + COL4_5 + " TEXT, " +  COL4_6 + " TEXT, " + COL4_7 + " INTEGER, " + COL4_8 + " TEXT)";
        String stworztabele5 = "CREATE TABLE " + TABLE_NAME5 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL5_2 + " INTEGER, " + COL5_3 + " TEXT, " + COL5_4 + " INTEGER, " + COL5_5 + " TEXT)";
        String stworztabele6 = "CREATE TABLE " + TABLE_NAME6 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL6_2 + " TEXT, " + COL6_3 + " INTEGER, " + COL6_4 + " INTEGER, " + COL6_5 + " INTEGER)";

        db.execSQL(stworztabele);
        db.execSQL(stworztabele2);
        db.execSQL(stworztabele3);
        db.execSQL(stworztabele4);
        db.execSQL(stworztabele5);
        db.execSQL(stworztabele6);
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

    public boolean dodajtekst2(String zasob, Integer kwota, String nazwaprzychodu) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 = new ContentValues();
        zawartosc2.put(COL2_2, zasob);
        zawartosc2.put(COL2_3, kwota);
        zawartosc2.put(COL2_4, nazwaprzychodu);
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

    public boolean dodajtekst4(String kiedypow, String kiedypowczas, String czestotliwosc, String kiedydane, String czywlaczone, long interwal, String czywlaczone2) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc4 = new ContentValues();
        zawartosc4.put(COL4_2, kiedypow);
        zawartosc4.put(COL4_3, kiedypowczas);
        zawartosc4.put(COL4_4, czestotliwosc);
        zawartosc4.put(COL4_5, kiedydane);
        zawartosc4.put(COL4_6, czywlaczone);
        zawartosc4.put(COL4_7, interwal);
        zawartosc4.put(COL4_8, czywlaczone2);
        sqLitebaza.insert(TABLE_NAME4, null, zawartosc4);
        return true;
    }

    public boolean dodajtekst5(Integer data, String wydatek, Integer kwota, String cheatday) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc5 = new ContentValues();
        zawartosc5.put(COL5_2, data);
        zawartosc5.put(COL5_3, wydatek);
        zawartosc5.put(COL5_4, kwota);
        zawartosc5.put(COL5_5, cheatday);
        sqLitebaza.insert(TABLE_NAME5, null, zawartosc5);
        return true;
    }

    public boolean dodajtekst6(String miesiac, Integer przychod, Integer wydatek, int oszczednosci) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc6 = new ContentValues();
        zawartosc6.put(COL6_2, miesiac);
        zawartosc6.put(COL6_3, przychod);
        zawartosc6.put(COL6_4, wydatek);
        zawartosc6.put(COL6_5, oszczednosci);
        sqLitebaza.insert(TABLE_NAME6, null, zawartosc6);
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

    public boolean zaaktualizujtekst2(String zasob, Integer kwota, String nazwaprzychodu) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc2 = new ContentValues();
        zawartosc2.put(COL2_2, zasob);
        zawartosc2.put(COL2_3, kwota);
        zawartosc2.put(COL2_4, nazwaprzychodu);
        sqLitebaza.update(TABLE_NAME2, zawartosc2, COL2_1 + " = (SELECT max(" + COL2_1 + ") FROM " + TABLE_NAME2 + ")", null);
        return true;
    }

    public boolean zaaktualizujtekst3(String wydatek, Integer kwota) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc3 = new ContentValues();
        zawartosc3.put(COL3_2, wydatek);
        zawartosc3.put(COL3_3, kwota);
        sqLitebaza.update(TABLE_NAME3, zawartosc3, COL3_1 + " = (SELECT max(" + COL3_1 + ") FROM " + TABLE_NAME3 + ")", null);
        return true;
    }

    public boolean zaaktualizujtekst4(String kiedypow, String kiedypowczas, String czestotliwosc, String kiedydane, String czywlaczone, long interwal, String czywlaczone2) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc4 = new ContentValues();
        zawartosc4.put(COL4_2, kiedypow);
        zawartosc4.put(COL4_3, kiedypowczas);
        zawartosc4.put(COL4_4, czestotliwosc);
        zawartosc4.put(COL4_5, kiedydane);
        zawartosc4.put(COL4_6, czywlaczone);
        zawartosc4.put(COL4_7, interwal);
        zawartosc4.put(COL4_8, czywlaczone2);
        sqLitebaza.update(TABLE_NAME4, zawartosc4, COL4_1 + "=ID", null);
        return true;
    }

    public boolean zaaktualizujtekst5(Integer data, String wydatek, Integer kwota, String cheatday) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc5 = new ContentValues();
        zawartosc5.put(COL5_2, data);
        zawartosc5.put(COL5_3, wydatek);
        zawartosc5.put(COL5_4, kwota);
        zawartosc5.put(COL5_5, cheatday);
        sqLitebaza.update(TABLE_NAME5, zawartosc5, COL5_1 + " = (SELECT max(" + COL5_1 + ") FROM " + TABLE_NAME5 + ")", null);
        return true;
    }

    public boolean zaaktualizujtekstcash(Integer ID, Integer data, String wydatek, Integer kwota, String cheatday) {
        SQLiteDatabase sqLitebaza = this.getWritableDatabase();
        ContentValues zawartosc5 = new ContentValues();
        zawartosc5.put(COL5_2, data);
        zawartosc5.put(COL5_3, wydatek);
        zawartosc5.put(COL5_4, kwota);
        zawartosc5.put(COL5_5, cheatday);
        sqLitebaza.update(TABLE_NAME5, zawartosc5, COL5_1 + " = ?", new String[] {ID.toString()});
        return true;
    }

    public Integer usunwszystko5() {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME5, null, null);
    }


    public Integer usuntekst2(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME2, COL2_1 + " = (SELECT max(" + COL2_1 + ") FROM " + TABLE_NAME2 + ")", null);
    }

    public Integer usuntekst3(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME3, COL3_1 + " = (SELECT max(" + COL3_1 + ") FROM " + TABLE_NAME3 + ")", null);
    }

    public Integer usuntekst4(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME4, COL4_1 + " = (SELECT max(" + COL4_1 + ") FROM " + TABLE_NAME4 + ")", null);
    }

    public Integer usuntekst5(String id) {
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        return sqLitebaza.delete(TABLE_NAME5, COL5_1 + " = (SELECT max(" + COL5_1 + ") FROM " + TABLE_NAME5 + ")", null);
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

    public Cursor odczytajtekstcheatday5() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME5 + " WHERE cheatday = 'Tak'", null);
        return cursor;
    }

    public Cursor odczytajtekst6() {
        //uzyskujemy odczytywalna baze
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        //tworzymy kursor aby zaznaczyc wszystkie wartosci
        Cursor cursor = sqLitebaza.rawQuery("SELECT * FROM " + TABLE_NAME6, null);
        return cursor;
    }

    public int sumaprzychodu() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM " + TABLE_NAME2, null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumawydatkowstalych() {
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
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM Przychod WHERE zasob = 'Karta płatnicza'", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumagotowka() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL2_3 + ") FROM Przychod WHERE zasob = 'Gotówka'", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumawydatkow() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL5_4 + ") FROM " + TABLE_NAME5, null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumawydatkowdzisiejszych(Integer date) {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL5_4 + ") FROM " + TABLE_NAME5 + " WHERE " + COL5_2 + " = ?",  new String[] {date.toString()});
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumacheatday() {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL5_4 + ") FROM Wydatki WHERE cheatday = 'Tak'", null);
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int sumacheatdaydzisiaj(Integer date) {
        int suma = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT SUM(" + COL5_4 + ") FROM Wydatki WHERE cheatday = 'Tak' AND " + COL5_2 + "= ?", new String[] {date.toString()});
        if (cursor.moveToFirst()) {
            suma = cursor.getInt(0);
        }
        cursor.close();
        return suma;
    }

    public int odczytajmaxdate() {
        int maxid = 0;
        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
        Cursor cursor = sqLitebaza.rawQuery("SELECT MAX(" + COL5_2 + ") FROM " + TABLE_NAME5, null);
        maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        return maxid;
    }

//    public int datapowiadomienia() {
//        int datapow = 0;
//        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
//        Cursor cursor = sqLitebaza.rawQuery("SELECT MAX(" + COL4_2 + ") FROM " + TABLE_NAME4, null);
//        datapow = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
//        return datapow;
//    }
//
//    public int godzpowiadomienia() {
//        int godzpowiad = 0;
//        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
//        Cursor cursor = sqLitebaza.rawQuery("SELECT MAX(" + COL4_3 + ") FROM " + TABLE_NAME4, null);
//        godzpowiad = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
//        return godzpowiad;
//    }
//    public int godzdane() {
//        int godzdaneint = 0;
//        SQLiteDatabase sqLitebaza = this.getReadableDatabase();
//        Cursor cursor = sqLitebaza.rawQuery("SELECT MAX(" + COL4_5 + ") FROM " + TABLE_NAME4, null);
//        godzdaneint = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
//        return godzdaneint;
//    }
}