package org.poo.cb.commands;

import org.poo.cb.App;

public class ExchangeMoney implements Command {
    private final String email;
    private final String Moneda1;
    private final String Moneda2;
    private final double suma;

    public ExchangeMoney(String[] action) {
        this.email = action[2];
        this.Moneda1 = action[3];
        this.Moneda2 = action[4];
        this.suma = Double.parseDouble(action[5]);
    }

    @Override
    public void execute() {
        App app = App.getInstance();
        app.exchangeMoney(email, Moneda1, Moneda2, suma);
    }
}
