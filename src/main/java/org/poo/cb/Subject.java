package org.poo.cb;

public interface Subject {
    void registerObserver(Observer observer);
    void notifyObservers();
}
