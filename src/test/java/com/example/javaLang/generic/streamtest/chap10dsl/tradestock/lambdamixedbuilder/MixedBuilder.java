package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {
    public static Order forCustomer(String customer, TradeBuilder... builders) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(builders).forEach(b -> order.addTrade(b.trade));

        return order;
    }

    public static TradeBuilder buy(Consumer<TradeBuilder> comsumer) {
        return buildTrade(comsumer, Trade.Type.BUY);
    }

    public static TradeBuilder sell(Consumer<TradeBuilder> comsumer) {
        return buildTrade(comsumer, Trade.Type.SELL);
    }

    private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.setType(type);
        consumer.accept(builder);

        return builder;
    }

    @Test
    void mixedBuilderTest() {
        Order order = forCustomer("BigBank",
                buy(t -> t.quantity(80)
                        .stock("IBM")
                        .on("NYSE")
                        .at(125.00)

                    ),
                sell(t -> t.at(125.00)
                        .quantity(50)
                        .stock("GOOGLE")
                        .on("NASDAQ")
                )
        );

        System.out.println(order.getValue());
    }

}
