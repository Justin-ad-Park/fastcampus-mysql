package com.example.javaLang.generic.nosideeffect.a_declarativeprogramming;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class _2_DeclarativeTest {

    private record Transantion (int value) {};

    List<Transantion> transactions = List.of(new Transantion(10),
            new Transantion(20),
            new Transantion(30));

    /**
     * 어떻게 로직을 구현하느냐에 집중하는
     * 고전적인 명령형(절차형) 프로그래밍 방식
     */
    @Test
    void getMostExpensive_classic() {
        Transantion mostExpensive = transactions.get(0);

        if(mostExpensive == null) throw new IllegalArgumentException("Empty list of transactions");

        for(Transantion t: transactions.subList(1, transactions.size())) {
            if(t.value() > mostExpensive.value())
                mostExpensive = t;
        }

        System.out.println(mostExpensive.value());
    }

    /**
     * '무엇을'에 집중하는 선언형 프로그래밍 방식
     * 질의문을 통해 문제를 해결하는 방식으로
     * 코드의 의도가 명확하게 드러난다는 장점이 있다.
     * (즉, 코드를 이해하기 쉽고 유지보수가 수월하다.)
     */
    @Test
    void getMostExpensive_lambda() {
        Optional<Transantion> mostExpensive =
                transactions.stream().max(Comparator.comparing(Transantion::value));

        System.out.println(mostExpensive);
    }

    /**
     * 함수형 프로그래밍은 선언형 프로그래밍을 따르는 대표적인 방식이다.
     * 함수형 프로그래밍은 부작용 없는 계산을 지향한다.
     * 부작용 없는 계산이란 상태를 가지지 않고, 참조값에 변경을 가하는 side effect이 없는 계산 방식이며,
     * 스레드 동기화에 영향이 없어서, 리액티브 프로그래밍에도 장점을 가진다.
     *
     * 리액티브 프로그램밍
     * 비동기 방식으로 CPU 활용성을 높이고, 응답속도를 최소화하는 방식으로
     *
     * 전통적인 방식이 메서드의 응답속도의 합이 요청에 대한 응답속도 였다면,
     * 리액티브 프로그래밍은 이상적으로는 메서드 중에서 응답속도가 가장 늦은 메서드의 응답속도가
     * 요청에 대한 응답속도가 되며, CPU(쓰레드) 활용면에서도 최대 효율을 낼 수 있다.
     */
}
