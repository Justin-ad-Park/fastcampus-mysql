package com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LambdaObserver {
    @FunctionalInterface
    interface Observer<T> {
        void notify(T message);
    }

    interface Subject<T> {
        void registerObserver(Observer<T> o);
        void notifyObservers(T message);
    }

    class WeatherFeed implements Subject<Integer> {
        private final List<Observer<Integer>> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer<Integer> o) {
            observers.add(o);
        }

        @Override
        public void notifyObservers(Integer degreesCelsius) {
            System.out.println("\n--새로운 온도 통지--");
            observers.forEach(o -> o.notify(degreesCelsius));
        }
    }


    @Test
    void 날씨통보테스트() throws InterruptedException {
        WeatherFeed wf = new WeatherFeed();

        // 뉴스채널
        wf.registerObserver((Integer degree) -> {
            System.out.println("News : 기온이 " + degree + "도 입니다.");
        });

        // 아래 코드에는 오류가 있음
        wf.registerObserver(
                (Integer degree) -> {
                    final String name = "철수네";
                    final int onDegree4Airconditioner = 29;
                    final int offDegree4Airconditioner = 28;
                    boolean onAirconditioner = false;

                    if(degree >= onDegree4Airconditioner && onAirconditioner == false) {
                        System.out.println(name + " : 에어컨 On");
                        onAirconditioner = true;
                    } else if(degree <= offDegree4Airconditioner && onAirconditioner) {
                        System.out.println(name + " : 에어컨 Off");
                        onAirconditioner = false;
                    }
                }
        );


        wf.notifyObservers(28);
        Thread.sleep(2000);
        wf.notifyObservers(30);
        Thread.sleep(2000);
        wf.notifyObservers(29);
        Thread.sleep(2000);
        wf.notifyObservers(28);
        Thread.sleep(2000);
        wf.notifyObservers(27);










        // [중요]옵저버가 상태를 가지는 경우 람다 표현식을 사용하면 안된다.!!!
        // 위의 코드와 같이 상태(에어컨 켜진 여부)를 가지는 경우 람다 표현식을 사용하면
        // 상태를 저장하지 못해서 의도와 다르게 오동작을 하게 된다.
    }
}
