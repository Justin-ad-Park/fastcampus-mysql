package com.example.javaLang.generic.streamtest.chap10dsl.tradestock;

import org.junit.jupiter.api.Test;

public class TradeStockTest {
    @Test
    void 도메인객체를이용한주식거래() {
        Order order = new Order();
        order.setCustomer("BigBank");


        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        Trade trade1 = new Trade();
        trade1.setType(Trade.Type.BUY);
        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);

        order.addTrade(trade1);


        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);
        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);

        order.addTrade(trade2);
    }
}
