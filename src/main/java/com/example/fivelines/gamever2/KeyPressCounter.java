package com.example.fivelines.gamever2;

public class KeyPressCounter {
    private int keyPressCount = 0;

    public void countKeyPress() {
        keyPressCount++;
    }

    public int getKeyPressCount() {
        return keyPressCount;
    }
}
