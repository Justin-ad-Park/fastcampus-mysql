package com.example.javaLang.generic.streamtest.collect.custom;

import org.junit.jupiter.api.Test;

import java.util.List;

public class CollectorTest {

    @Test
    void test() {
        List<String> strings = List.of("Hello", " ", "World", "!");

        String result = strings.stream()
                .collect(new StringJoinerCollector());

        System.out.println(result);  // 출력: Hello World!

    }

    @Test
    void test2() {
        List<String> strings = List.of("Hello", " ", "World", "!");

        String result = strings.stream()
                .collect(StringBuilder::new, StringBuilder::append, (s1,s2) -> s1.append(s2) ).toString();

        System.out.println(result);  // 출력: Hello World!

    }
}
