package com.example.javaLang.generic.functional;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PeekTest {

    @Test
    void test() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        numbers.stream()
                .map(x -> x + 17)
                .filter(x -> x % 2 == 0)
                .limit(3)
                .forEach(System.out::println);
    }

    @Test
    void peekTest() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        var result = numbers.stream()
                .peek(x -> System.out.println("from stream : " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map : " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter : " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit : " + x))
                .collect(Collectors.toList());

        System.out.println("\n=== Result ===");
        result.forEach(System.out::println);

    }

}
