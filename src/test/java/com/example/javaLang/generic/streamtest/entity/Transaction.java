package com.example.javaLang.generic.streamtest.entity;

public class Transaction {

    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(final Trader trader, final int year, final int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%1$s, year: $2$d, value: %3$d", this.trader, this.year, this.value);
    }


}
