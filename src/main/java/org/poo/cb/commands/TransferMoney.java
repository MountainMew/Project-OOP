package org.poo.cb.commands;

import org.poo.cb.App;

public class TransferMoney implements Command {
    String email;
    String Moneda1;
    String Moneda2;
    double suma;

    public TransferMoney(String[] action) {
        this.email = action[2];
        this.Moneda1 = action[3];
        this.Moneda2 = action[4];
        this.suma = Double.parseDouble(action[5]);
    }

    public void execute() {
        App app = App.getInstance();
        app.transferMoney(email, Moneda1, Moneda2, suma);
    }
}
