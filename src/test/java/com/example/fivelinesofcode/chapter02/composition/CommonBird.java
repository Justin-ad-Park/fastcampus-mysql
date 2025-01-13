package com.example.fivelinesofcode.chapter02.composition;

public class CommonBird implements Bird {

    @Override
    public boolean hasBeak() {
        return true;
    }

    @Override
    public boolean canFly() {
        return true;
    }
}
