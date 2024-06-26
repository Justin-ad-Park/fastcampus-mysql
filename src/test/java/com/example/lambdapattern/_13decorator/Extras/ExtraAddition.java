package com.example.lambdapattern._13decorator.Extras;

public abstract class ExtraAddition {
    public abstract String name();
    public abstract int cost();

    @Override
    public String toString() {
        return name();
    }

}
