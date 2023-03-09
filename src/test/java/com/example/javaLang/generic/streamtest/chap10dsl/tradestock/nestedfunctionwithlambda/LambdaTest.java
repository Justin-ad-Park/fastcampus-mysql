package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunctionwithlambda;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import org.junit.jupiter.api.Test;

import static com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunctionwithlambda.NestedFunctionOrderBuilderWithLambda.*;


public class LambdaTest {
    @Test
    void 중첩함수_람다이용_명확화() {
        Order order = order("myCustomer",
                buy(quantity(100), stock("IBM", "NYSE"), price(125.00)),
                sell(quantity(50), stock("GOOGLE", "NASDAQ"), price(375.00))
        );

        System.out.println(order.getValue());
    }
}
