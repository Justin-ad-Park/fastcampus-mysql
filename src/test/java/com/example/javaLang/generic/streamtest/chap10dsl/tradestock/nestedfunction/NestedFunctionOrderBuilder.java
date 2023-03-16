package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunction;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Stock;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class NestedFunctionOrderBuilder {
    public static Order order(String customer, Trade... trades) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(trades).forEach(order::addTrade); //주문에 모든 거래 추가
        return order;
    }

    public static Trade buy(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }

    public static Trade sell(int quantity, Stock stock, double price) {
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }

    private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
        Trade trade = new Trade();
        trade.setQuantity(quantity);
        trade.setType(type);
        trade.setStock(stock);
        trade.setPrice(price);

        return trade;
    }

    public static double at(double price) {
        return price;
    }

    public static Stock stock(String symbol, String market) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setMarket(market);
        return stock;
    }

    public static String on(String market) {
        return market;
    }

    @Test
    void 중첩함수이용() {
        Order order = order("BigBank",
                buy(80, stock("IBM", on("NYSE")) , at(125.00)),
                sell(50, stock("GOOGLE", on("NASDAQ")), at(375.00))
        );

        System.out.println(order.getValue());
    }
}