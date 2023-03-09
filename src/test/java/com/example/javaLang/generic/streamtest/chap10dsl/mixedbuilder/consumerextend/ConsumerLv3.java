package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerLv3 {


    public static Consumer<String> printHeader(String head) {
        return s -> System.out.printf(head + s);

    }

    @NotNull
    private Consumer<String> printTail(String tail) {
        return s -> System.out.println(tail);
    }


    @Test
    void Test() {
        Stream<String> stream = Stream.of("A", "B", "C");
        stream.forEach(printHeader("Head:"));
    }


    @Test
    void Test2() {
        Stream<String> stream = Stream.of("A", "B", "C");
        stream.forEach(printHeader("<Head>").andThen(printTail("<Tail>")));
    }

}
