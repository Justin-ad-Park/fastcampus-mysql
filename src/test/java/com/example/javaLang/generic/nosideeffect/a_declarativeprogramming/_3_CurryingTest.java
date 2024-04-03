package com.example.javaLang.generic.nosideeffect.a_declarativeprogramming;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;

/**
 * <pre>
 * 커리화된 함수(Curried function)는 다수의 인자를 받는 함수를 하나의 인자만 받는 함수들의 연속으로 변환하는 프로세스를 말합니다. 이 개념은 하스켈 커리(Haskell Curry)의 이름을 따서 명명되었으며, 함수형 프로그래밍에서 자주 사용됩니다.
 *
 * 커리화는 다음과 같은 이유로 유용합니다:
 *
 * 부분 적용(Partial application): 함수의 일부 인자만을 적용하여, 나머지 인자를 받을 준비가 된 새로운 함수를 생성할 수 있습니다. 이를 통해 코드의 재사용성을 높일 수 있습니다.
 *
 * 코드의 간결성: 커리화를 사용하면 더 간결하고 읽기 쉬운 코드를 작성할 수 있습니다. 함수의 인자를 차례로 적용함으로써, 복잡한 표현식을 더 이해하기 쉽게 만들 수 있습니다.
 *
 * 함수 조합(Function composition): 커리화는 함수를 더 작은 단위로 분해하여, 이러한 작은 함수들을 조합하는 데 유리합니다. 이는 함수형 프로그래밍의 핵심 원칙 중 하나인 함수 조합을 쉽게 만들어 줍니다.
 * 예를 들어, 세 개의 인자를 받는 함수 f(x, y, z)가 있다고 할 때, 이 함수를 커리화하면 f(x)(y)(z)와 같이 각각의 인자를 받는 함수의 연속으로 변환됩니다. 첫 번째 함수는 x를 받고, 두 번째 함수는 y를 받으며, 마지막 함수는 z를 받아 최종 결과를 반환합니다. 각 단계에서 반환된 함수는 다음 인자를 기다리는 상태가 됩니다.
 * </pre>
 */
public class _3_CurryingTest {

    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    @Test
    void classicalConverTest() {
        double input = 10;

        System.out.println("섭씨 -> 화씨 변환");
        System.out.println(converter(input, 9.0 / 5, 10));

        System.out.println("Km -> Mile 변환");
        System.out.println(converter(input, 0.621371, 0));

        System.out.println("USD -> GBP(영국파운드)");
        System.out.println(converter(input, 0.6, 0));


    }

    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
        //return converter(x, f, b);    //위와 결과 동일
    }

    DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
    DoubleUnaryOperator convertKmtoMi = curriedConverter(0.621371, 0);
    DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);


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
