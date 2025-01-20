package com.example.fivelines.gamever3;

public class KeyPressCounter {
    private int keyPressCount = 0;

    public void countKeyPress() {
        keyPressCount++;
    }

    public int getKeyPressCount() {
        return keyPressCount;
    }
}
