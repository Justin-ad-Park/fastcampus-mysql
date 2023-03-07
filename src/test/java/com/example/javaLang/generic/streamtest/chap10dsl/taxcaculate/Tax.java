package com.example.javaLang.generic.streamtest.chap10dsl.taxcaculate;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;
import org.junit.jupiter.api.Test;

import static com.example.javaLang.generic.streamtest.chap10dsl.tradestock.lambdamixedbuilder.MixedBuilder.*;

public class Tax {
    public static double regional(double value) {
        return value * 1.1;
    }

    public static double general(double value) {
        return value * 1.3;
    }

    public static double surcharge(double value) {
        return value * 1.05;
    }

    //
    public static double calculate(Order order,boolean useRegional,
                                   boolean useGeneral, boolean useSurchage) {
        double value = order.getValue();
        if(useRegional) value = Tax.regional(value);
        if(useGeneral) value = Tax.general(value);
        if(useSurchage) value = Tax.surcharge(value);

        return value;
    }

    @Test
    void test() {
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


        double value = calculate(order, true, false, true);
        System.out.println(String.format("거래금액 %1$5.2f, 세금포함 %2$5.2f", order.getValue(), value));
    }
}
