package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.pizzaorder;

public class Topping {
    private final String name;

    public Topping(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Topping : " + name;
    }
}