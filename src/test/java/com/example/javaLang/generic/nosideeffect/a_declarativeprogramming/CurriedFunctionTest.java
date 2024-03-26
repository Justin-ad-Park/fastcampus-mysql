package com.example.javaLang.generic.nosideeffect.a_declarativeprogramming;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class CurriedFunctionTest {

    Function<String, Function<Integer, String>> repeatLetterCurriedFunction = letter ->
            repeatCount -> {
                return letter.repeat(repeatCount);
            };

    @Test
    void curriedTest() {
        String result = repeatLetterCurriedFunction.apply("AB ").apply(3);

        System.out.println(result);
    }

    @FunctionalInterface
    interface CharacterFunction<T, R> {
        R character(T t);
    }

    @FunctionalInterface
    interface RepeatFunction<T, R> {
        R repeat(T t);
    }

    CharacterFunction<String, RepeatFunction<Integer, String>> repeatStringFunction = str ->
            repeatCount -> str.repeat(repeatCount);

    @Test
    void curriedTest2() {
        String result = repeatStringFunction.character("AB ").repeat(3);

        System.out.println(result);
    }
}
