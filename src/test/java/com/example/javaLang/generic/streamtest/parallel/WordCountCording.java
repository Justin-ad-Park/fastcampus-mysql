package com.example.javaLang.generic.streamtest.parallel;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCountCording {
    static Pattern punctuation = Pattern.compile("[\\p{P}“”‘’™•]"); //구두점 문자 + 특수문자
    static Pattern whitespace = Pattern.compile("[\\p{Z}]"); //공백 문자 + 대시 문자(—)
    static Pattern word = Pattern.compile("[\\p{L}\\p{N}]+");   //word = 유니코드 문자(알파벳 포함), 숫자, 밑줄이 하나 이상 연속된 문자열을 매칭

    public static Map<String, Integer> wordCount(String path) {
        Path filePath = Paths.get(path);
        Map<String, Integer> result = null;

        try(Stream<String> stream = Files.lines(filePath)) {
            result = stream.parallel()
                    .map(punctuation::matcher)
                    .map(matcher -> matcher.replaceAll(""))
                    .map(whitespace::split)
                    .flatMap(Arrays::stream)
                    .filter(word -> !word.isBlank())
                    .filter(s -> word.matcher(s).matches())
                    .map(String::toLowerCase)
                    .collect(CountKey());

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static @NotNull Collector<String, ?, Map<String, Integer>> CountKey() {
        return Collectors.toMap(
                Function.identity()
                , w -> 1
                , Integer::sum
        );
    }

    @Test
    void wordCountTest() {
        String path = "src/main/resources/war-and-peace.txt";
        Map<String, Integer> result = wordCount(path);

        result.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(System.out::println);
    }


}
