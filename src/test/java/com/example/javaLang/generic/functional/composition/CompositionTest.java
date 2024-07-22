package com.example.javaLang.generic.functional.composition;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class CompositionTest {

    @Test
    void Test() {
        UnaryOperator<String> removeLowerCaseA = str -> str.replace("a","");
        UnaryOperator<String> upperCase = String::toUpperCase;

        var input = "abcd";

        var result = removeLowerCaseA.andThen(upperCase).apply(input);
        var result2 = upperCase.compose(removeLowerCaseA).apply(input);

        System.out.println(result);
        System.out.println(result2);
    }

    @Test
    void CompositorTest() {
        UnaryOperator<String> removeLowerCaseA = str -> str.replace("a","");
        UnaryOperator<String> upperCase = String::toUpperCase;

        Function<String, String> stringOperations = removeLowerCaseA.andThen(upperCase);

        Consumer<String> task = Compositor.compose(stringOperations, System.out::println);

        task.accept("abcd");

    }
}
