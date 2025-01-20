package com.example.fivelines.gamever3;

public interface GameStatusObserver<T> {
    void gameSuccess(T score);
}
