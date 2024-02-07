package org.poo.cb.commands;

import org.poo.cb.App;

public class ListPortfolio implements Command {
    private final String email;

    public ListPortfolio(String[] action) {
        this.email = action[2];
    }

    @Override
    public void execute() {
        App app = App.getInstance();
        app.listPortfolio(email);
    }
}
