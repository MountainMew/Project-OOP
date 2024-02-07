package org.poo.cb.commands;

import org.poo.cb.App;

public class BuyStocks implements Command {
    String email;
    String nume_companie;
    int numar_actiuni;

    public BuyStocks(String[] action) {
        this.email = action[2];
        this.nume_companie = action[3];
        this.numar_actiuni = Integer.parseInt(action[4]);
    }

    @Override
    public void execute() {
        App app = App.getInstance();
        app.buyStocks(email, nume_companie, numar_actiuni);
    }
}
