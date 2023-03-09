package com.example.javaLang.generic.streamtest.chap10dsl.taxcaculate;

import com.example.javaLang.generic.streamtest.chap10dsl.tradestock.Order;

public class Tax {
    public static double regional(double value) {
        System.out.println("지방세 10%");
        return value * 1.1;
    }

    public static double general(double value) {
        System.out.println("종합과세 30%");
        return value * 1.3;
    }

    public static double surcharge(double value) {
        System.out.println("할증료 5%");
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
}
