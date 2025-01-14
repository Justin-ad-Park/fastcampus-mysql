package com.example.filvelines.input;

public class Up implements Input {
    @Override
    public boolean isRight() { return false; }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isUp() {
        return true;
    }

    @Override
    public boolean isDown() {
        return false;
    }
}
