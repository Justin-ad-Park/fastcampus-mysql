package com.example.javaLang.generic.streamtest.collect.testresult;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class TeeingTest {


    @Test
    void test() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Using Collectors.teeing to calculate the sum and average of the numbers
        Result result = numbers.stream()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.filtering(n -> n % 2 == 0,
                        Collectors.averagingInt(Integer::intValue)),
                        Result::new
                ));

        System.out.println("Sum: " + result.sum);
        System.out.println("Average: " + result.average);
    }

    record Result (int sum, double average) {}
}
