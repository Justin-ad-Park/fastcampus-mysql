package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

public class TradeBuilder {
    protected Trade trade = new Trade();

    public void setType(Trade.Type type) {
        trade.setType(type);
    }

    public TradeBuilder quantity(int quantity) {
        trade.setQuantity(quantity);
        return this;
    }

    public TradeBuilder at(double price) {
        trade.setPrice(price);
        return this;
    }

    public StockBuilder stock(String symbol) {
        return new StockBuilder(this, trade, symbol);
    }

}