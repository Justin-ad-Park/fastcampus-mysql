package com.example.javaLang.generic.streamtest.chap10dsl._practice;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

public class ChainingOrderBuilder {
    private final Order order = new Order();

    public ChainingOrderBuilder(String customerName) {
        order.setCustomer(customerName);
    }

    public static ChainingOrderBuilder forCustomer(String customerName) {
        return new ChainingOrderBuilder(customerName);
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, order, Trade.Type.BUY, quantity);
    }
}
