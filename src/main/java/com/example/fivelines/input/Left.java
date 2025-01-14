package com.example.fivelines.input;

public class Left implements Input {
    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public boolean isLeft() { return true; }

    @Override
    public boolean isUp() {
        return false;
    }

    @Override
    public boolean isDown() {
        return false;
    }
}
