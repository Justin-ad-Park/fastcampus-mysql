package com.example.javaLang.generic.streamtest.chap10dsl._practice;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;


public class TradeBuilder {
    private final ChainingOrderBuilder orderBuilder;
    private final Order order;
    private Trade trade = new Trade();

    public TradeBuilder(ChainingOrderBuilder orderBuilder, Order order, Trade.Type type, int quantity) {
        this.orderBuilder = orderBuilder;
        this.order = order;

        trade.setType(type);
        trade.setQuantity(quantity);
        order.addTrade(trade);
    }

    public StockBuilder stock(String stockNM) {
        return new StockBuilder(orderBuilder, trade, stockNM);
    }
}
