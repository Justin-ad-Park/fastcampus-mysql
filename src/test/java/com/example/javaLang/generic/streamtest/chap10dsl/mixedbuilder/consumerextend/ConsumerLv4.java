package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsumerLv4 {

    public class StringArray {
        private List<String> strs = new ArrayList<>();

        public void add(String str) {
            strs.add(str);
        }

        @Override
        public String toString() {
            return String.join(",", strs);
        }

        public String toString2() {
            return strs.stream().collect(Collectors.joining(", "));
        }

    }

    public static Consumer<String> addToStrs(StringArray sa) {
        return str -> sa.add(str);
    }


    @Test
    void Test() {
        Stream<String> stream = Stream.of("A", "B", "C");
        StringArray sa = new StringArray();

        stream.forEach(addToStrs(sa));

        System.out.println(sa);
        System.out.println(sa.toString2());
    }


}
