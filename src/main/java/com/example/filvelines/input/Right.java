package com.example.filvelines.input;

public class Right implements Input {
    @Override
    public boolean isRight() { return true; }

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
        return false;
    }
}
