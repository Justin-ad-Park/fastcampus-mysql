package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunction;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import org.junit.jupiter.api.Test;

import static com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunction.NestedFunctionOrderBuilder.*;


public class NestedFunctionTest {

    @Test
    void 중첩함수이용() {
        Order order = order("BigBank",
                buy(80, stock("IBM", on("NYSE")) , at(125.00)),
                sell(50, stock("GOOGLE", on("NASDAQ")), at(375.00))
                );

        System.out.println(order.getValue());
    }
}
