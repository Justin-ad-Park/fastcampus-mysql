package com.example.javaLang.generic.lambdapattern._11observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class OriginalObserverPattern {
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
            this.observers.add(o);
        }

        @Override
        public void notifyObservers(Integer degreesCelsius) {
            System.out.println("\n--새로운 온도 통지--");
            observers.forEach(o -> o.notify(degreesCelsius));
        }
    }

    //뉴스 채널
    class NewsChannel implements Observer<Integer> {

        @Override
        public void notify(Integer degree) {
            System.out.println("News : 기온이 " + degree + "도 입니다.");
        }
    }

    //Home Automation
    class HomeAutomation implements Observer<Integer> {
        final String name;
        final int onDegree4Airconditioner;
        final int offDegree4Airconditioner;
        boolean onAirconditioner = false;

        HomeAutomation(final String name, final int onDegree4Airconditioner, final int offDegree4Airconditioner) {
            this.name = name;
            this.onDegree4Airconditioner = onDegree4Airconditioner;
            this.offDegree4Airconditioner = offDegree4Airconditioner;
        }


        @Override
        public void notify(Integer degreesCelsius) {
            if(degreesCelsius >= onDegree4Airconditioner && onAirconditioner == false) {
                System.out.println(name + " : 에어컨 On");
                onAirconditioner = true;
            } else if(degreesCelsius <= offDegree4Airconditioner && onAirconditioner) {
                System.out.println(name + " : 에어컨 Off");
                onAirconditioner = false;
            }
        }
    }

    @Test
    void 날씨통보테스트() throws InterruptedException {
        WeatherFeed wf = new WeatherFeed();

        wf.registerObserver(new NewsChannel());
        wf.registerObserver(new HomeAutomation("영희네", 28, 27));
        wf.registerObserver(new HomeAutomation("철수네", 30, 28));


        wf.notifyObservers(28);
        Thread.sleep(2000);
        wf.notifyObservers(30);
        Thread.sleep(2000);
        wf.notifyObservers(29);
        Thread.sleep(2000);
        wf.notifyObservers(28);
        Thread.sleep(2000);
        wf.notifyObservers(27);

    }
}
