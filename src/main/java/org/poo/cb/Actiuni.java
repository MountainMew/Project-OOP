package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class Actiuni {
    String nume_companie;
    List<Double> valori = new ArrayList<>();
    int numar_actiuni = 0;
    Double medie;
    public Actiuni(String numeCompanie) {
        this.nume_companie = numeCompanie;
    }
}

