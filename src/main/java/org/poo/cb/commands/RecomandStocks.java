package org.poo.cb.commands;

import org.poo.cb.App;

public class RecomandStocks implements Command {
    @Override
    public void execute() {
        App app = App.getInstance();
        app.recommendStocks();
        app.afiisareRecomandari();
    }
}
