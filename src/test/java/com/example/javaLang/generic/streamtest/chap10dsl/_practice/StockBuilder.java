package com.example.javaLang.generic.streamtest.chap10dsl._practice;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Stock;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

public class StockBuilder extends Stock {
    private final ChainingOrderBuilder orderBuilder;
    private Trade trade;
    private Stock stock = new Stock();

    public StockBuilder(ChainingOrderBuilder orderBuilder, Trade trade, String apple) {
        this.orderBuilder = orderBuilder;
        this.trade = trade;

        stock.setSymbol(apple);
        this.trade.setStock(stock);
    }
}
