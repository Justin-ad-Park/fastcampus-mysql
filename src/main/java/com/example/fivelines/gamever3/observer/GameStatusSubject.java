package com.example.fivelines.gamever3.observer;

public interface GameStatusSubject<T> {
    void registerObserver(GameStatusObserver<T> o);
    void notifyObservers(T message);
}
