package org.poo.cb;

import java.util.ArrayList;
import java.util.List;
public class Utilizator implements Observer {
    String email;
    String nume;
    String prenume;
    String adresa;
    Portofoliu_Actiuni portofoliu_actiuni;
    Portofoliu_Conturi portofoliu_conturi;
    List<Utilizator> prieteni;
    List<Actiuni> recommandari = new ArrayList<>();
    public Utilizator(String email, String nume, String prenume, String Adresa) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = Adresa;
        this.portofoliu_actiuni = new Portofoliu_Actiuni();
        this.portofoliu_conturi = new Portofoliu_Conturi();
        this.prieteni = new ArrayList<>();
    }

    @Override
    public void update() {
        this.recommandari = Recomandari.recomandari;
    }
}
