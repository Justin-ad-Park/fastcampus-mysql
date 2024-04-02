package com.example.javaLang.generic.lambdapattern._01strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OriginalStrategyPattern {

    //인터페이스 선언
    public interface ValidationStrategy {
        boolean execute(String s);
    }

    //클래스 구현 - 소문자 검증
    private class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    //클래스 구현 - 숫자 검증
    private class IsNumeric implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    //주입을 통해 SOLID
    private class Validator {
        private final ValidationStrategy strategy;

        private Validator(final ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

    //테스트
    @Test
    void numericValidateTest() {
        Validator numericValidator = new Validator(new IsNumeric());
        boolean isNumeric = numericValidator.validate("1245");

        Assertions.assertTrue(isNumeric);
    }

    @Test
    void lowerCaseValidateTest() {
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean isLowerCase = lowerCaseValidator.validate("abcdef");

        Assertions.assertTrue(isLowerCase);
    }

}