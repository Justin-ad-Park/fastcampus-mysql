package com.example.javaLang.generic.nosideeffect.a_declarativeprogramming;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;

public class CurryingTest {

    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
        //return converter(x, f, b);    //위와 결과 동일
    }

    DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
    DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
    DoubleUnaryOperator convertKmtoMi = curriedConverter(0.621371, 0);

    @Test
    void convertTest() {
        System.out.println("섭씨 -> 화씨 변환");
        System.out.println(convertCtoF.applyAsDouble(10));

        System.out.println("Km -> Mile 변환");
        System.out.println(convertKmtoMi.applyAsDouble(10));

        System.out.println("USD -> GBP(영국파운드)");
        System.out.println(convertUSDtoGBP.applyAsDouble(10));
    }

    @Test
    void PrimeNumber() {
        System.out.println(Math.floor(Math.sqrt(24)));

        System.out.println(Math.ceil(Math.sqrt(24)));
    }

}
