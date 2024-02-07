package org.poo.cb.commands;

import org.poo.cb.App;

public class AddAccount implements Command {
    private final String email;
    private final String Moneda;

    public AddAccount(String[] action) {
        this.email = action[2];
        this.Moneda = action[3];
    }

    @Override
    public void execute() {
        App app = App.getInstance();
        app.addAccount(email, Moneda);
    }
}
