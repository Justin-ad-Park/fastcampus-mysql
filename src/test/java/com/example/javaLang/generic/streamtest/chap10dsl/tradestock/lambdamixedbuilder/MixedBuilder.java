package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Trade;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {

    /**
     * forEach의 Consumer가 소비해야 할 함수로
     * b -> order.addTrade(b.getTrade()) 가 람다식으로 제공했다.
     *
     * b는 당연히 스트림의 TradeBuilder 객체이며,
     * 이 객체는 forCustomer의 두 번째 이후 인자로 배열로 받았다.
     *
     * @param customer
     * @param builders
     * @return
     */
    public static Order forCustomer(String customer, TradeBuilder... builders) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(builders).forEach(b -> order.addTrade(b.getTrade()));

        return order;
    }

    /**
     * buy,sell 메서드는 Consumer 함수형 인터페이스를 메서드 참조로 받는다.
     *  이 메서드는 tradeBuilder를 accept(tradeBuilder)로 최종 소비한다.
     *  리턴값은 tradeBuilder이다.
     *
     *  즉, 리턴된 tradeBuilder의 기능(멤버변수, 메서드 등)을 이용할 수 있다.
     * @param consumer
     * @return
     */
    public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.BUY);
    }

    public static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.SELL);
    }

    /**
     * buildTrade는 consumer 함수를 실제로 호출하는 accept(builder)를 가지고 있다.
     * consumer가 소비해야 하는 buildTrade는 외부에서 주입을 받지 않았고,
     * buildTrade 메서드 내부에서 생성해서 accept 함수에 주입했다.
     *
     * accept(builder)에서는
     *  주입받은 아래 함수를 실행한다.
     *      t -> t.quantity(80)
     *          .stock("IBM")
     *          .on("NYSE")
     *          .at(125.00)
     *  즉, TradeBuilder를 통해 Trade와 Stock 등을 셋팅한다.
     *
     * @param consumer
     * @param type
     * @return
     */
    private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.setType(type);
        consumer.accept(builder);

        return builder;
    }

    @Test
    void mixedBuilderTest() {
        Order order = forCustomer("BigBank",
                buy(
                        t -> t.quantity(80)
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

    @Test
    void mixedBuilderTest2() {
        Order order = forCustomer("BigBank",
                buy(t -> t.stock("IBM")
                        .on("NYSE")
                        .at(125.00)
                ),
                sell(t -> t.stock("GOOGLE")
                ),
                sell(t -> t.stock("IBM")
                ),
                buy(t -> t.quantity(50)
                ),
                sell(t -> t.at(100.00)
                )
        );

        System.out.println(order.getValue());
    }

}
