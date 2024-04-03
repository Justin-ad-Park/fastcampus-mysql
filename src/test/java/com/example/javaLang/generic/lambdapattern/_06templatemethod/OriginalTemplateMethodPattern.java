package com.example.javaLang.generic.lambdapattern._06templatemethod;

import org.junit.jupiter.api.Test;

public class OriginalTemplateMethodPattern {
    abstract class OnlineBanking {
        public void deposit(int amount) {
            // 입금 공통 로직은 여기에 있다.
            System.out.println("입금액: " + amount + "원");
            bearInterest(amount);
        }

        // 인터넷 뱅킹 업체마다 선이자 계산 방식이 다른 경우 템플릿 메소드로 분리
        abstract void bearInterest(int amount);
    }

    private class CocoaTalkOnlineBanking extends OnlineBanking {
        double interestRate = 0.01;

        @Override
        void bearInterest(int amount) {
            System.out.println("코코아톡뱅킹이자: " + Math.round(amount * interestRate) + "원");
        }
    }

    @Test
    void 코코아톡입금테스트() {
        CocoaTalkOnlineBanking banking = new CocoaTalkOnlineBanking();

        banking.deposit(999);
    }
}
