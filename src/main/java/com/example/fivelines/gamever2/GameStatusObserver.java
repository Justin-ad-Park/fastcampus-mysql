package com.example.fivelines.gamever2;

public interface GameStatusObserver<T> {
    void gameSuccess(T score);
}
