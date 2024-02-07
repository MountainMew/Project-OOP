package org.poo.cb;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class App {
    private static App instance = null;
    private final Recomandari recomandari = new Recomandari();
    public static Map<String, Utilizator> utilizatori = new HashMap<>();
    public static Map<String, Actiuni> actiuni = new HashMap<>();
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public void createUser(String s, String s1, String s2, String s3) {
        if(checkIfUserExists(s)) {
            System.out.println("User with " + s + " already exists");
        } else {
            Utilizator utilizator = new Utilizator(s, s1, s2, s3);
            utilizatori.put(s, utilizator);
            recomandari.registerObserver(utilizator);
        }
    }

    private boolean checkIfUserExists(String s) {
        if(utilizatori == null) {
            return false;
        }
        for (Utilizator utilizator : utilizatori.values()) {
            if (utilizator.email.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public void listUser(String email) {
        for (Utilizator utilizator : utilizatori.values()) {
            if (utilizator.email.equals(email)) {
                System.out.print("{" + '"' + "email" + '"' + ":" + '"' + utilizator.email + '"' + ','
                        + '"' + "firstname" + '"' + ':' + '"' + utilizator.nume + '"' + ','
                        + '"' + "lastname" + '"' + ':' + '"' + utilizator.prenume + '"' + ',' +
                        '"' + "address" + '"' + ':' + '"' + utilizator.adresa + '"' + ',' +
                        '"' + "friends" + '"' + ':' + '[');
                for(Utilizator prieten : utilizator.prieteni) {
                    System.out.print('"' + prieten.email + '"');
                    if(utilizator.prieteni.indexOf(prieten) != utilizator.prieteni.size() - 1) {
                        System.out.print(",");
                    }
                }
                System.out.println("]}");
                return;
            }
        }
        System.out.println("user with email " + email + " doesn't exist");
    }

    public void addFriend(String email, String email_friend) {
        if(utilizatori.containsKey(email) && utilizatori.containsKey(email_friend)) {
            utilizatori.get(email).prieteni.add(utilizatori.get(email_friend));
            utilizatori.get(email_friend).prieteni.add(utilizatori.get(email));
        } else {
            System.out.println("user with email " + email + " doesn't exist");
        }
    }

    public void addAccount(String email, String moneda) {
        if(checkIfAccountExists(email, moneda)) {
            System.out.println("Account already exists");
        } else {
            Cont cont = new Cont(moneda);
            utilizatori.get(email).portofoliu_conturi.add(cont);
        }
    }

    private boolean checkIfAccountExists(String email, String moneda) {
        for (Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
            if (cont.tip.equals(moneda)) {
                return true;
            }
        }
        return false;
    }
    public void addMoney(String email, String moneda, double suma) {
        if(checkIfAccountExists(email, moneda)) {
            for (Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
                if (cont.tip.equals(moneda)) {
                    cont.suma += suma;
                    return;
                }
            }
        } else {
            System.out.println("Account does not exist");
        }
    }

    public void listPortfolio(String email) {
        if(utilizatori.containsKey(email)) {
            System.out.print("{\"stocks\":[");
            for(Actiuni actiune : utilizatori.get(email).portofoliu_actiuni.actiuni) {
                System.out.print("{\"stockname\":\"" + actiune.nume_companie + "\",\"amount\":" + actiune.numar_actiuni + "}");
                if(utilizatori.get(email).portofoliu_actiuni.actiuni.indexOf(actiune) != utilizatori.get(email).portofoliu_actiuni.actiuni.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("],");
            System.out.print("\"accounts\":[");
            for(Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
                String formattedSuma = String.format("%.2f", cont.suma);
                System.out.print("{\"currencyname\":\"" + cont.tip + "\",\"amount\":\"" + formattedSuma + "\"}" );
                if(utilizatori.get(email).portofoliu_conturi.conturi.indexOf(cont) != utilizatori.get(email).portofoliu_conturi.conturi.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("]}");
            System.out.println();
        } else {
            System.out.println("user with email " + email + " doesn't exist");
        }
    }

    public void exchangeMoney(String email, String moneda1, String moneda2, double suma) {
        Path path = Paths.get("src/main/resources/common/exchangeRates.csv");
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path.toString()));
            String line = br.readLine();
            String[] header = line.split(",");
            line = br.readLine();
            while(line != null) {
                String[] exchangeRate = line.split(",");
                if(exchangeRate[0].equals(moneda2)) {
                    for(int i = 1; i < 6; i++) {
                        if(header[i].equals(moneda1)) {
                            double exchange = Double.parseDouble(exchangeRate[i]);
                            for (Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
                                if (cont.tip.equals(moneda1)) {
                                    if(cont.suma >= suma) {
                                        suma *= exchange;
                                        cont.suma -= suma;
                                        if(cont.suma * 0.5 < suma) {
                                            cont.suma = cont.suma - suma * 0.01;
                                        }
                                        for (Cont cont1 : utilizatori.get(email).portofoliu_conturi.conturi) {
                                            if (cont1.tip.equals(moneda2)) {
                                                suma /= exchange;
                                                cont1.suma += suma;
                                                return;
                                            }
                                        }
                                    } else {
                                        System.out.println("Insufficient amount in account " + moneda1 + " for exchange");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void transferMoney(String email, String emailFriend, String moneda, double suma) {
        if(checkIfFriendExists(email, emailFriend)) {
            for (Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
                if (cont.tip.equals(moneda)) {
                    if(cont.suma >= suma) {
                        cont.suma -= suma;
                        for (Cont cont1 : utilizatori.get(emailFriend).portofoliu_conturi.conturi) {
                            if (cont1.tip.equals(moneda)) {
                                cont1.suma += suma;
                                return;
                            }
                        }
                    } else {
                        System.out.println("Insufficient amount in account " + moneda + " for transfer");
                    }
                }
            }
        } else {
            System.out.println("You are not allowed to transfer money to" + emailFriend);
        }
    }

    private boolean checkIfFriendExists(String email, String emailFriend) {
        if(utilizatori.containsKey(email) && utilizatori.containsKey(emailFriend)) {
            for(Utilizator prieten : utilizatori.get(email).prieteni) {
                if(prieten.email.equals(emailFriend)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void recommendStocks() {
        for(Actiuni actiune : actiuni.values()) {
            recomandari.recomandari(actiune);
        }
    }

    public void readStockValues(Path path) {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path.toString()));
            String line = br.readLine();
            line = br.readLine();
            while(line != null) {
                double suma = 0.0;
                String[] stock = line.split(",");
                String nume_companie = stock[0];
                Actiuni actiune = new Actiuni(nume_companie);
                for(int i = 1; i < stock.length; i++) {
                    actiune.valori.add(Double.parseDouble(stock[i]));
                    suma += Double.parseDouble(stock[i]);
                }
                actiuni.put(nume_companie, actiune);
                actiune.medie = suma / (stock.length - 1);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void afiisareRecomandari() {
        System.out.print("{" + '"' + "stocksToBuy" + '"' + ':' + '[');
        for(Actiuni actiune : Recomandari.recomandari) {
            System.out.print('"' + actiune.nume_companie + '"');
            if(Recomandari.recomandari.indexOf(actiune) != Recomandari.recomandari.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }

    public void buyStocks(String email, String numeCompanie, int numar_actiuni) {
        if(utilizatori.containsKey(email) && actiuni.containsKey(numeCompanie)) {
            double suma = actiuni.get(numeCompanie).valori.getLast() * numar_actiuni;
            for (Cont cont : utilizatori.get(email).portofoliu_conturi.conturi) {
                if (cont.tip.equals("USD")) {
                    if(cont.suma >= suma) {
                        cont.suma -= suma;
                        utilizatori.get(email).portofoliu_actiuni.actiuni.add(actiuni.get(numeCompanie));
                        actiuni.get(numeCompanie).numar_actiuni += numar_actiuni;
                        return;
                    } else {
                        System.out.println("Insufficient amount in account for buying stock");
                    }
                }
            }
        } else {
            System.out.println("user with email " + email + " doesn't exist");
        }

    }
    public void clear() {
        for(Utilizator utilizator : utilizatori.values()) {
            if(utilizator.portofoliu_actiuni != null) {
                utilizator.portofoliu_actiuni.clear();
            }
            if(utilizator.portofoliu_conturi != null) {
                utilizator.portofoliu_conturi.clear();
            }
            if(utilizator.prieteni != null) {
                utilizator.prieteni.clear();
            }
            if(utilizator.recommandari != null) {
                utilizator.recommandari.clear();
            }
        }
        recomandari.clearObservers();
        for(Actiuni actiune : actiuni.values()) {
            if(actiune.valori != null) {
                actiune.valori.clear();
            }
        }
        actiuni.clear();
        utilizatori.clear();
    }
}
