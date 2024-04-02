package com.example.javaLang.generic.lambdapattern._11observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObserverByConsumer {

    public class TemperatureSensor {
        private final String sensorName;
        private int celsius;
        private List<Consumer> subscribers = new ArrayList<>();

        public TemperatureSensor(final String sensorName) {
            this.sensorName = sensorName;
        }

        public void onChange(int newCelsius) {
            this.celsius = newCelsius;
            System.out.println("--센서-->" + this.sensorName + " : " + this.celsius);

            notificationSubscripbers();
        }

        public void clearSubscribers() {
            System.out.println("--모든 구독 제거--");
            subscribers.clear();
        }

        //구독자 등록
        public void subscribe(Consumer<? super Integer> subscriber) {
            this.subscribers.add(subscriber);
        }

        public void notificationSubscripbers() {
            subscribers.forEach(subscriber -> subscriber.accept(this.celsius));
        }

    }

    // Consumer 소비 방식 1 : 인터페이스를 구현한 클래스
    public class CelsiusDisplayPannel implements Consumer<Integer> {

        @Override
        public void accept(Integer newCelsius) {
            System.out.println("[디스플레이패널]온도가 " + newCelsius + "로 변경이 되었습니다.");
        }
    }

    // Consumer 소비 방식 2 : 인터페이스를 구현한 람다 메서드
    public Consumer<Integer> sendMessage = newCelsius ->
            System.out.println("[SendMessage]메시지 발송 : " + newCelsius);

    /**
     *  Consumer 소비 방식 3 : 인자와 리턴값이 일치하는 메서드
     *
     *  Consumer 인터페이스를 구현하지 않았더라도 인자와 리턴값이 일치하는 메서드라면 Consumer의 메서드 참조로 사용할 수 있다.
     *  이 경우 메서드명이 accept가 아니여도 인자와 리턴값만 일치하면 메서드 참조 인자(파라미터)로 사용이 가능하다.
     */
    public class NotificationSlack {
        private final String channelName;

        public NotificationSlack(final String channelName) {
            this.channelName = channelName;
        }

        public void notify(int newCelsius) {
            System.out.println("[" + this.channelName +"] 온도 : " + newCelsius);
        }
    }

    @Test
    void test() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");

        // 함수형 인터페이스를 구현한 클래스 방식
        CelsiusDisplayPannel dispPannel = new CelsiusDisplayPannel();
        ts.subscribe(dispPannel);
        ts.onChange(25);

        // 함수형 인터페이스를 구현한 람다 메서드 전달 방식
        System.out.println("\n메시지 발송 추가");
        ts.subscribe(sendMessage);
        ts.onChange(27);


        //함수형 인터페이스와 형식(인자, 리턴값)이 동일한 메서드를 참조로 제공하는 방식
        System.out.println("\n슬랙채널 추가");
        NotificationSlack notiSlack = new NotificationSlack("사무실 온도 채널");
        ts.subscribe(notiSlack::notify);
        ts.onChange(26);

        ts.clearSubscribers();

        //람다 메서드를 직접 제공하는 방식
        System.out.println("\n람다 메서드로 슬랙 채널 추가");
        NotificationSlack notiSlack2 = new NotificationSlack("테스트 채널");

        ts.subscribe(
                (newValue) -> {notiSlack2.notify(newValue);}
        );
        ts.onChange(27);

        ts.clearSubscribers();

        //람다 메서드를 직접 제공하는 방식
        System.out.println("\n람다 메서드 제공2");
        ts.subscribe(newValue -> System.out.println("[람다 메서드] 온도 표시 : " + newValue));
        ts.onChange(30);


    }

    @Test
    void Test_AndThen() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");

        var sendMessageAndThen = sendMessage.andThen(value -> System.out.println("[And Then] : " + value));
        ts.subscribe(sendMessageAndThen);
        ts.onChange(25);

        ts.clearSubscribers();

        ts.subscribe(sendMessage);

        Consumer<Integer> message2 = sendMessage.andThen(value -> System.out.println("[New And Then] : " + value) );
        ts.subscribe(message2);

        ts.onChange(31);
    }

    @Test
    void Test_AndThen2() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");
        ts.subscribe(sendMessage);
        sendMessage.andThen(value -> System.out.println("[New And Then] : " + value) );
        ts.onChange(31);
    }

    @Test
    void Test_andThen3() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");

        // 함수형 인터페이스를 구현한 클래스 방식
        CelsiusDisplayPannel dispPannel = new CelsiusDisplayPannel();
        ts.subscribe(dispPannel.andThen(System.out::println));
        ts.onChange(25);
        ts.onChange(26);
        }

    @Test
    void Test_andThen4() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");
        NotificationSlack notiSlack = new NotificationSlack("사무실 온도 채널");
        // ts.subscribe(notiSlack::notify.andThen(System.out::println));
        ts.onChange(26);
        ts.onChange(26);
    }

    @Test
    void Test_andThen5() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");
        NotificationSlack notiSlack = new NotificationSlack("사무실 온도 채널");
        ts.subscribe(
                ((Consumer<Integer>)value -> System.out.println("Display:" + value)).andThen(value -> System.out.println())
                );

        ts.onChange(26);
        ts.onChange(26);
    }

    @Test
    void Test_andThen6() {
        TemperatureSensor ts = new TemperatureSensor("사무실 온도계");
        NotificationSlack notiSlack = new NotificationSlack("사무실 온도 채널");
        ts.subscribe(
                ((Consumer<Integer>)value -> notiSlack.notify(value)).andThen(value -> System.out.println())
        );

        ts.onChange(26);
        ts.onChange(26);
    }
}

