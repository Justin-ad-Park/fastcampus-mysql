package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.methodchain;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Stock;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

public class StockBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    public StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
        this.builder = builder;
        this.trade = trade;
        stock.setSymbol(symbol);
    }

    public TradeBuilderWithStock on(String market) {
        stock.setMarket(market);
        trade.setStock(stock);
        return new TradeBuilderWithStock(builder, trade);
    }
}
