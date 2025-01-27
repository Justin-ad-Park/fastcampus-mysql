package com.example.javaLang.generic.streamtest.entity;

import java.util.Arrays;
import java.util.List;

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

    public static List<Transaction> getTransactionList() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");


        return Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }



}
