package com.example.javaLang.generic.functional.composition;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.*;

public final class Compositor {
    public static <T, R> Supplier<R> compose(Supplier<T> before,
                                             Function<T, R> fn) {
        Objects.requireNonNull(before);
        Objects.requireNonNull(fn);

        return () -> {
            T result = before.get();
            return fn.apply(result);
        };
    }

    public static <T,R> Consumer<T> compose(Function<T,R> fn, Consumer<R> after) {
        Objects.requireNonNull(fn);
        Objects.requireNonNull(after);

        return (T t) -> {
            R result = fn.apply(t);
            after.accept(result);
        };
    }

    public static <T> Consumer<T> acceptIf(Predicate<T> predicate,
                                       Consumer<T> consumer) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(consumer);

        return(T t) -> {
            if(!predicate.test(t)) {
                return;
            }

            consumer.accept(t);
        };
    }

    @Test
    void CompositorComposeTest1() {
        Supplier<String> alphabet = () -> "abcd";
        UnaryOperator<String> upperCase = String::toUpperCase;

        Supplier<String> task = compose(alphabet, upperCase);

        String result = task.get();

        System.out.println(result);

    }


    @Test
    void CompositorComposeTest2() {
        UnaryOperator<String> removeLowerCaseA = str -> str.replace("a","");
        UnaryOperator<String> upperCase = String::toUpperCase;

        Function<String, String> stringOperations = removeLowerCaseA.andThen(upperCase);

        Consumer<String> task = compose(stringOperations, System.out::println);

        task.accept("abcd");

    }

    @Test
    void CompositorAcceptIf() {
        Predicate<Integer> biggerThan5 = i -> i > 5;
        Consumer<Integer> printInt = i -> System.out.println(i);

        Consumer<Integer> printIntIfBiggerThan5 = acceptIf(biggerThan5, printInt);

        printIntIfBiggerThan5.accept(6);
    }
}
