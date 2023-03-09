package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerLv2 {


    public static Consumer<String> println(String head) {
        return s -> System.out.println(head + s);

    }

    @Test
    void Test() {
        Stream<String> stream = Stream.of("A", "B", "C");
        stream.forEach(println("Head:"));
    }

    @Test
    void Test2() {
        Stream<String> stream = Stream.of("A", "B", "C");
        stream.forEach(println("Head:").andThen(println("Tail:")));
    }

}
