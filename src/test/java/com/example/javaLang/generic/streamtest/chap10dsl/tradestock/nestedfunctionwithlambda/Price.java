package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunctionwithlambda;

public class Price<Double> extends SimpleSupplier {
    protected Price(Double value) {
        super(value);
    }
}