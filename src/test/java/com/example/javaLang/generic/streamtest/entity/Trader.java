package com.example.javaLang.generic.streamtest.entity;

public class Trader {
    private final String name;
    private final String city;

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Trader(final String name, final String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader: " + this.name + " in " + this.city;
    }
}
