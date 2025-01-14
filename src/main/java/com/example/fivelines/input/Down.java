package com.example.fivelines.input;

public class Down implements Input {
    @Override
    public boolean isRight() { return false; }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isUp() {
        return false;
    }

    @Override
    public boolean isDown() {
        return true;
    }
}
