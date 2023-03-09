package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunctionwithlambda;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Stock;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class NestedFunctionOrderBuilderWithLambda {
    public static Order order(String customer, Trade... trades) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(trades).forEach(order::addTrade); //주문에 모든 거래 추가
        return order;
    }

    public static Trade buy(Quantity quantity, Stock stock, Price price) {
        return buildTrade(quantity, stock, price, Trade.Type.BUY);
    }

    public static Trade sell(Quantity quantity, Stock stock, Price price) {
        return buildTrade(quantity, stock, price, Trade.Type.SELL);
    }

    private static Trade buildTrade(Supplier<Integer> quantity, Stock stock, Supplier<Double> price, Trade.Type type) {
        Trade trade = new Trade();
        trade.setQuantity(quantity.get());
        trade.setType(type);
        trade.setStock(stock);
        trade.setPrice(price.get());

        return trade;
    }

    public static Stock stock(String symbol, String market) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setMarket(market);
        return stock;
    }

    public static Price<Double> price(Double price) {
        return new Price(price);
    }

    public static Quantity<Integer> quantity(Integer quantity) {
        return new Quantity(quantity);
    }
}
