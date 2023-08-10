package com.example.javaLang.generic.streamtest.chap10dsl._practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MethodChainingTest2 {
    final String CUSTOMER_NAME = "Justin";

    /*

        Order order = ChaningOrderBuilder.forCustomer("Justin")
            .buy(100)
            .stock("Apple")
            .Market("KOSPI")
            .priceAt(9900)
            .sell(50)
            .stock("MS")
            .Market("KOSDAQ")
            .priceAt(5000)
            .end();
     */
    @Test
    void choBuilderTest() {
        ChainingOrderBuilder b1 = ChainingOrderBuilder.forCustomer(CUSTOMER_NAME);
        Assertions.assertNotNull(b1);

        TradeBuilder b2 = b1.buy(100);
        Assertions.assertNotNull(b2);

        StockBuilder b3 = b2.stock("Apple");



    }





}
