package com.example.fivelines.gamever3.observer;

public interface GameStatusObserver<T> {
    void gameSuccess(T score);
}
