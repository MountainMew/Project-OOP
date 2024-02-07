package org.poo.cb.commands;

import org.poo.cb.App;

public class CreatUser implements Command {
    private String email;
    private String nume;
    private String prenume;
    private String adresa;

    public CreatUser(String[] args) {
        this.email = args[2];
        this.nume = args[3];
        this.prenume = args[4];
        StringBuilder adresa = new StringBuilder(args[5]);
        for (int i = 6; i < args.length; i++) {
            adresa.append(" ").append(args[i]);
        }
        this.adresa = adresa.toString();
    }

    public void execute() {
        App app = App.getInstance();
        app.createUser(email, nume, prenume, adresa);
    }
}
