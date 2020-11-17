package com.example.provistudent;

public class ScreenItem {
    String Tytul, Opis;
    int Zdjecieintro;

    public ScreenItem(String tytul, String opis, int zdjecieintro) {
        Tytul = tytul;
        Opis = opis;
        Zdjecieintro = zdjecieintro;
    }

    public void setTytul(String tytul) {
        Tytul = tytul;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public void setZdjecieintro(int zdjecieintro) {
        Zdjecieintro = zdjecieintro;
    }

    public String getTytul() {
        return Tytul;
    }

    public String getOpis() {
        return Opis;
    }
    public int getZdjecieintro() {
        return Zdjecieintro;
    }
}
