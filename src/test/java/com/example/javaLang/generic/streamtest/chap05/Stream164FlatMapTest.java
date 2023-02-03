package com.example.javaLang.generic.streamtest.chap05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream164FlatMapTest {

    String[] arrayOfWords = {"Goodbye", "World"};
    Supplier<Stream<String>> streamOfwords = () -> Arrays.stream(arrayOfWords);



    @Test
    void mapTest() {
        var resultStream = streamOfwords.get()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        resultStream.forEach(v -> v.forEach(System.out::println));
    }

    @Test
    void flatMapTest() {
        var resultStream = streamOfwords.get()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        resultStream.forEach(System.out::println);
    }

    @Test
    void 페이지165_퀴즈1() {
        var numbers = Arrays.asList(1, 2, 3, 4, 5);

        var squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    @Test
    void 페이지165_퀴즈2() {
        var numbers1 = Arrays.asList(1,2,3);
        var numbers2 = Arrays.asList(3,4);

        var result = numbers1.stream()
                .flatMap(x -> numbers2.stream().map(y -> new int[]{x, y}))
                .collect(Collectors.toList());

        result.stream()
                .forEach( i ->
                        {Arrays.stream(i).asLongStream().forEach(System.out::print);
                            System.out.println();
                        }
                );
    }

    @Test
    void 페이지165_퀴즈3() {
        var numbers1 = Arrays.asList(1,2,3);
        var numbers2 = Arrays.asList(3,4);

        List<int[]> result = numbers1.stream()
                .flatMap(x ->
                    numbers2.stream()
                            .filter(y -> (x + y) % 3 == 0)
                            .map(y -> new int[]{x,y})
                )
                .collect(Collectors.toList());

        result.stream()
                .forEach( i ->
                        {Arrays.stream(i).asLongStream().forEach(System.out::print);
                            System.out.println();
                        }
                );

    }


}
