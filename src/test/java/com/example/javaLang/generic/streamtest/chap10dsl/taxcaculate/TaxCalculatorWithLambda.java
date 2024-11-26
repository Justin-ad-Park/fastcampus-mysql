package com.example.javaLang.generic.streamtest.chap10dsl.taxcaculate;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculatorWithLambda {
    //기본 세금계산 함수는 원래 금액을 그대로 리턴한다.
    // 즉, 세금을 계산하지 않는다.
    public DoubleUnaryOperator taxFunction = d -> {
        System.out.println("Default 비과세");
        return d;
    };

    public TaxCalculatorWithLambda with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f);
        return this;
    }

    public double calculate(Order order) {
        return taxFunction.applyAsDouble(order.getValue());
    }
}
