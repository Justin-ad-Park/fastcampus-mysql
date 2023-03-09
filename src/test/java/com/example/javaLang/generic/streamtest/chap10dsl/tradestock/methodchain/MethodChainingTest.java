package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.methodchain;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import org.junit.jupiter.api.Test;

public class MethodChainingTest {
    @Test
    void 메서드체인_트레이드_코스() {
        Order order = MethodChainingOrderBuilder.forCustomer("BigBank")
                .buy(80)
                .stock("IBM")
                .on("NYSE")
                .at(125.00)
                .sell(50)
                .stock("GOOGLE")
                .on("NASDAQ")
                .at(375.00)
                .end();

        System.out.println(order.getValue());
    }
}
