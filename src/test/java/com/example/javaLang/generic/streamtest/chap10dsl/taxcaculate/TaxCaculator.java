package com.example.javaLang.generic.streamtest.chap10dsl.taxcaculate;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;

public class TaxCaculator {
    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;

    public TaxCaculator withTaxRegional() {
        useRegional = true;
        return this;
    }

    public TaxCaculator withTaxGeneral() {
        useGeneral = true;
        return this;
    }

    public TaxCaculator withTaxSurcharge() {
        useSurcharge = true;
        return this;
    }

    public double calculate(Order order) {
        return Tax.calculate(order, useRegional, useGeneral, useSurcharge);
    }
}
