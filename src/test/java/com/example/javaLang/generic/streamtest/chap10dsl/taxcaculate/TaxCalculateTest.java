package com.example.javaLang.generic.streamtest.chap10dsl.taxcaculate;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import org.junit.jupiter.api.Test;

import static com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder.MixedBuilder.*;

public class TaxCalculateTest {
    private final Order order;

    public TaxCalculateTest() {
        order = forCustomer("BigBank",
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
    }

    private void printBill(double value) {
        System.out.println(String.format("거래금액 %1$5.2f, 세금포함 %2$5.2f", order.getValue(), value));
    }

    @Test
    void test() {
        double value = Tax.calculate(order, true, false, true);
        printBill(value);
    }


    @Test
    void TaxCaculatorTest() {
        double value = new TaxCalculator().withTaxGeneral().withTaxSurcharge()
                .calculate(order);

        printBill(value);
    }

    @Test
    void TaxCaculatorWithLambdaTest() {
        double value = new TaxCalculatorWithLambda()
                .calculate(order);
        printBill(value);
    }

    @Test
    void TaxCaculatorWithLambdaTest2() {
        double value = new TaxCalculatorWithLambda()
                .with(Tax::regional)
                .with(Tax::surcharge)
                .calculate(order);
        printBill(value);
    }

}
