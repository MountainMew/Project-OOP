package org.poo.cb.commands;

import org.poo.cb.App;

public class ListUser implements Command {

    String email;

    public ListUser(String[] args) {
        this.email = args[2];
    }
    @Override
    public void execute() {
        App app = App.getInstance();
        app.listUser(email);
    }
}
