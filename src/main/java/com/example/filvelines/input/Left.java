package com.example.filvelines.input;

public class Left implements Input2 {
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
