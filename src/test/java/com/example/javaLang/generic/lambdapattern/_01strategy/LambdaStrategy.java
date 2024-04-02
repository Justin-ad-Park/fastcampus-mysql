package com.example.javaLang.generic.lambdapattern._01strategy;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class LambdaStrategy {
    // 인터페이스 정의 - Predicate<T>를 사용해되 되나, 메서드명이 validate()가 더 직관적이니까
    @FunctionalInterface
    public interface Validator<T> {
        boolean validate(T t);
    }

    // 메서드 참조 생성
    Validator<String> lowerCaseValidator = (String s) -> s.matches("[a-z]+");
    Validator<String> numericValidator = (String s) -> s.matches("\\d+");

    //테스트
    @Test
    void lowerCaseTest() {
        boolean isLowerCase = lowerCaseValidator.validate("abcde");
        Assertions.assertTrue(isLowerCase);
    }

    @Test
    void numericTest() {
        boolean isNumeric = numericValidator.validate("12345");
        Assertions.assertTrue(isNumeric);
    }


    // 메서드 참조 생성
    Predicate<String> lowerCaseValidator2 = (String s) -> s.matches("[a-z]+");
    Predicate<String> numericValidator2 = (String s) -> s.matches("\\d+");

    //테스트
    @Test
    void lowerCaseTest2() {
        boolean isLowerCase = lowerCaseValidator2.test("abcde");
        Assertions.assertTrue(isLowerCase);
    }

    @Test
    void numericTest2() {
        boolean isNumeric = numericValidator2.test("12345");
        Assertions.assertTrue(isNumeric);
    }


}
