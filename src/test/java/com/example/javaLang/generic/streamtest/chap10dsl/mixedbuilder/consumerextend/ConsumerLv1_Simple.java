package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerLv1_Simple {

    Consumer<Integer> add = num -> System.out.println(num + 10);

    Consumer<Integer> sleep = num -> {
        System.out.println("Sleep :" + num);

        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    Consumer<Integer> sleep10x = num -> {
        num = num * 10;
        System.out.println("Sleep10x :" + num);

        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };


    @Test
    void simpleConsumerTest() {
        add.accept(10);
    }

    @Test
    void simpleConsumerTest2() {
        add.andThen(sleep10x).andThen(sleep).accept(10);
    }

    @Test
    void simpleConsumerTest3() {
        Stream<Integer> intStream = Stream.of(1,2,3);

        intStream.forEach(add.andThen(sleep10x).andThen(sleep));
    }

}
