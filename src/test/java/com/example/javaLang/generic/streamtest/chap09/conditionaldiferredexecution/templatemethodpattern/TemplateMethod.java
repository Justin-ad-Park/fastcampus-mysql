package com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.templatemethodpattern;

import org.junit.jupiter.api.Test;
import java.util.function.Consumer;

public class TemplateMethod {
    public void deposit(int amount, Consumer<Integer> bearInterest) {
        System.out.println("입금액: " + amount + "원");
        bearInterest.accept(amount);
    }

    Consumer<Integer> bearInterest4CocoaTalk = (Integer amount) -> {
        double interestRate = 0.01;
        System.out.println("코코아톡뱅킹이자: " + Math.round(amount * interestRate) + "원");
    };

    @Test
    void 템플릿메소드_람다메서드참조사용() {
        deposit( 999, bearInterest4CocoaTalk);
    }

    @Test
    void 템플릿메서드_람다표현식() {
        deposit( 999, (amount) -> {
                double interestRate = 0.01;
                System.out.println("코코아톡뱅킹이자: " + Math.round(amount * interestRate) + "원");
            });
    }
}
