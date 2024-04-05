package com.example.javaLang.generic.lambdapattern._02templatemethod;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * <pre>
 * <b><font color=red>템플릿 메서드 패턴을 람다 패턴으로 변경하는 경우</font></b>에는
 * 전통적이니 템플릿 메서드 패턴에서 추상 클래스의 추상 메서드로 인터페이스를 만드는 역할을
 * 람다 함수(함수형 인터페이스)가 대신하기 때문에 엄밀히 말하면 더 이상 템플릿 메서드 패턴은 아니고
 * 전통적인 패턴중에서 <b><font color=red>전략 패턴(Strategy pattern)</font></b>으로 바뀌게 된다.
 *   템플릿 메서드 패턴과 전략 패턴은 알고리즘(로직)을 사용자측(클라이언트)에서 결정할 수 있다는 공통점이 있으며,
 *   구현 방식에서 추상 클래스의 추상 메서드를 사용하는지, 인터페이스의 구현을 사용하는지의 차이만 있다.
 * </pre>
 */
public class LambdaTemplateMethod {
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
