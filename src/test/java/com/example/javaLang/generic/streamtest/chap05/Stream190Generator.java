package com.example.javaLang.generic.streamtest.chap05;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream190Generator {


    @Test
    void iterateTest() {
        Stream.iterate(1, n -> n * 2)
            .limit(13)
            .forEach(System.out::println);
    }


    @Test
    void 피보나치수열() {
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1],t[0]+t[1]})
                .limit(13)
                .forEach(t -> System.out.println(String.format("%1d, %2d", t[0],t[1])));
    }


    private int[] generateFibonacci(int[] t) {
        return new int[]{t[1], t[0] + t[1]};
    }
    @Test
    void 피보나치수열1() {
        Stream.iterate(new int[]{0,1}, this::generateFibonacci)
                .limit(13)
                .forEach(t -> System.out.println(String.format("%1d, %2d", t[0],t[1])));
    }


    private static UnaryOperator<int[]> Fibonacci = (t) -> new int[]{t[1],t[0]+t[1]};

    @Test
    void 피보나치수열3() {
        Stream.iterate(new int[]{0,1}, Fibonacci)
                .limit(13)
                .forEach(t -> System.out.println(String.format("%1d, %2d", t[0],t[1])));
    }

    @Test
    void Generator2() {
        IntStream.iterate(0, n-> n <= 100, n->n+4)
            .forEach(System.out::println);
    }

    /*
        필터는 iterate가 생성하는 량을 제어하지 못하고, 무한으로 로직이 돌게됨
            limit이 없으면 의미없는 연산을 계속 하게됨
     */
    @Test
    void Generator3() {
        IntStream.iterate(0, n->n+4)
                .filter(t -> t < 100)
                .limit(100)
                .forEach(System.out::println);
    }

    @Test
    void generateStream() {
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }
}