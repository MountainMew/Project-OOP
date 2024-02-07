package org.poo.cb.commands;

import org.poo.cb.App;

public class AddMoney implements Command {
    private final String email;
    private final String Moneda;
    private final double suma;

    public AddMoney(String[] action) {
        this.email = action[2];
        this.Moneda = action[3];
        this.suma = Double.parseDouble(action[4]);
    }

    @Override
    public void execute() {
        App app = App.getInstance();
        app.addMoney(email, Moneda, suma);
    }
}
