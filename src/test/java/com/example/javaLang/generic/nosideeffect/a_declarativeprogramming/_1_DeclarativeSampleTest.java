package com.example.javaLang.generic.nosideeffect.a_declarativeprogramming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class _1_DeclarativeSampleTest {

    Predicate<Integer> isEven = (x) -> x % 2 == 0;
    Predicate<Integer> isOdd = (x) -> x % 2 != 0;

    private List<Integer> numbers = Arrays.asList(1,3,21,10,8,11);

    /**
     * 절차형 프로그래밍 방식
     */
    @Test
    void Example_Procedure() {
        int sum = 0;

        for(int number : numbers) {
            if (number > 6 && isOdd.test(number)) {
                sum += number;
            }
        }

        Assertions.assertEquals(32, sum);
    }

    /**
     * 선언적 프로그래밍 방식
     */
    @Test
    void Example_Declarative() {

        int sum = numbers.stream()
                .filter(x -> x > 6)
                .filter(isOdd)
                .mapToInt(x -> x)
                .sum();

        Assertions.assertEquals(32, sum);
    }



}
