package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class Recomandari implements Subject {
    List<Observer> observers = new ArrayList<>();
    static List<Actiuni> recomandari = new ArrayList<>();
    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }
    @Override
    public void notifyObservers() {
        for(Observer observer : this.observers) {
            observer.update();
        }
    }
    public void recomandari(Actiuni actiune) {
        double shortTermSMA = calculateSMA(actiune.valori.subList(actiune.valori.size() - 5, actiune.valori.size()));
        double longTermSMA = calculateSMA(actiune.valori.subList(actiune.valori.size() - 10, actiune.valori.size()));
        if(shortTermSMA > longTermSMA) {
            recomandari.add(actiune);
            notifyObservers();
        }
    }
    private double calculateSMA(List<Double> doubles) {
        double sum = 0;
        for (Double aDouble : doubles) {
            sum += aDouble;
        }
        return sum / doubles.size();
    }
    public void clearObservers() {
        this.observers.clear();
    }
}
