package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Stock;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

public class StockBuilder {
    private final TradeBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();


    public StockBuilder(TradeBuilder tradeBuilder, Trade trade, String symbol) {
        this.builder = tradeBuilder;
        this.trade = trade;
        this.stock.setSymbol(symbol);
    }

    public TradeBuilder on(String market) {
        stock.setMarket(market);
        trade.setStock(stock);
        return builder;
    }
}
