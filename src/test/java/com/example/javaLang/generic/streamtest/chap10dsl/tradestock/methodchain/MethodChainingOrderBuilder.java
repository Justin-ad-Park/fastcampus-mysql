package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.methodchain;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;

/**
 * MethodChainingOrderBuilder
 *  .forCustomer(String customer)
 *  return MethodChainingOrderBuilder
 *      .buy(int quantity)
 *      ->TradeBuilder(this, Type.BUY, quantity)
 *      .sell(int quantity)
 *      ->TradeBuilder(this, Type.SELL, quantity)
 *          .stock(String symbo)
 *          ->StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol)
 *              .on(String market)
 *              ->TradeBuilderWithStock(MethodChainingOrderBuilder builder, trade)
 *                  .at(double price)
 *                  ->MethodChainingOrderBuilder builder.addTrade(trade)
 *                      .end()
 */

public class MethodChainingOrderBuilder {
    public final Order order = new Order();

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    // 빌더 패턴
    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    // 빌더 패턴
    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade);
        return this;
    }

    // 주문 만들기를 종료하고 반환
    public Order end() {
        return order;
    }
}
