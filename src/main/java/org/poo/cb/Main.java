package org.poo.cb;

import org.poo.cb.commands.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String email;
        if(args == null) {
            System.out.println("Running Main");
            return;
        }
        App app = App.getInstance();
        Path path = Paths.get("src/main/resources/" + args[2]);
        String folder = args[2].split("/")[0];
        Path path1 = Paths.get("src/main/resources/" + folder + "/stockValues.csv");
        app.readStockValues(path1);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toString()));
            String line = br.readLine();
            while(line != null && !line.isEmpty()) {
                String[] action = line.split(" ");
                String actions = action[0] + " " + action[1];
                switch (actions) {
                    case "CREATE USER" -> {
                        CreatUser creatUser = new CreatUser(action);
                        creatUser.execute();
                    }
                    case "LIST USER" -> {
                        ListUser listUser = new ListUser(action);
                        listUser.execute();
                    }
                    case "ADD FRIEND" -> {
                        AddFriend addFriend = new AddFriend(action);
                        addFriend.execute();
                    }
                    case "ADD ACCOUNT" -> {
                        AddAccount addAccount = new AddAccount(action);
                        addAccount.execute();
                    }
                    case "ADD MONEY" -> {
                        AddMoney addMoney = new AddMoney(action);
                        addMoney.execute();
                    }
                    case "LIST PORTFOLIO" -> {
                        ListPortfolio listPortfolio = new ListPortfolio(action);
                        listPortfolio.execute();
                    }
                    case "EXCHANGE MONEY" -> {
                        ExchangeMoney exchangeMoney = new ExchangeMoney(action);
                        exchangeMoney.execute();
                    }
                    case "TRANSFER MONEY" -> {
                        TransferMoney transferMoney = new TransferMoney(action);
                        transferMoney.execute();
                    }
                    case "RECOMMEND STOCKS" -> {
                        RecomandStocks recomandStocks = new RecomandStocks();
                        recomandStocks.execute();
                    }
                    case "BUY STOCKS" -> {
                        BuyStocks buyStocks = new BuyStocks(action);
                        buyStocks.execute();
                    }
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        app.clear();
    }
}