package com.example.javaLang.generic.functional.replace;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class LikePredicateTest {

    @Test
    void Test() {
        LikePredicate<String> isNull = str -> str == null;
        //Predicate<String> wontCompile = isNull;

        Predicate<String> canCompile = isNull::test;

        System.out.println(canCompile.test(null));
        System.out.println(canCompile.test("A"));

    }

}
