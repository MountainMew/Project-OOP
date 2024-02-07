package org.poo.cb.commands;

import org.poo.cb.App;

public class AddFriend implements Command {
    private String email;
    private String email_friend;

    public AddFriend(String[] action) {
        this.email = action[2];
        this.email_friend = action[3];
    }
    @Override
    public void execute() {
        App app = App.getInstance();
        app.addFriend(email, email_friend);
    }
}
