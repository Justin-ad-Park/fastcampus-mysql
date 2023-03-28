package com.example.javaLang.generic.streamtest.chap17reactiveapplication.c_temperature;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureSubscriber;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureSubscription;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.b_temperature.TemperatureSubscriptionByExecutor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;

public class TemperatureTest {
    @Test
    void testWithBoth1() {
        getCelsiusTemperatures("New York")
                .subscribe(new TemperatureSubscriber());
    }

    public static Flow.Publisher<TemperatureInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            CelsiusConversionProcessor processor = new CelsiusConversionProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TemperatureSubscription(processor, town));
        };
    }

    /**
     * Subscriber만 등록한 경우
     */
    @Test
    void testWithSubscriber() {
        TemperatureSubscriber subscriber = new TemperatureSubscriber();
        TemperatureSubscription subscription = new TemperatureSubscription(subscriber, "New York");

        subscriber.onSubscribe(subscription);
    }

    /**
     * 섭씨 변환 프로세서를 경우하는 경우
     */
    @Test
    void testWithBoth2() {
        TemperatureSubscriber subscriber = new TemperatureSubscriber();
        CelsiusConversionProcessor processor = new CelsiusConversionProcessor();

        processor.subscribe(subscriber);

        TemperatureSubscription subscription = new TemperatureSubscription(processor, "New York");
        processor.onSubscribe(subscription);

    }

    /**
     * 섭씨 변환 프로세서와 각가 등록하면???
     */
    @Test
    void testWithBoth3() {
        TemperatureSubscriber subscriber = new TemperatureSubscriber();
        CelsiusConversionProcessor processor = new CelsiusConversionProcessor();

        processor.subscribe(subscriber);

        TemperatureSubscription subscription = new TemperatureSubscription(processor, "New York");
        processor.onSubscribe(subscription);


        TemperatureSubscriber seoulSubscriber = new TemperatureSubscriber();
        TemperatureSubscription seoulSubscription = new TemperatureSubscription(seoulSubscriber, "Seoul");
        seoulSubscriber.onSubscribe(seoulSubscription);
    }


    /**
     * 섭씨 변환 프로세서와 각가 등록하면???
     */
    @Test
    void testWithBoth4() {
        TemperatureSubscriber subscriber = new TemperatureSubscriber();
        CelsiusConversionProcessor processor = new CelsiusConversionProcessor();

        processor.subscribe(subscriber);

        TemperatureSubscriptionByExecutor subscription = new TemperatureSubscriptionByExecutor(processor, "New York");
        processor.onSubscribe(subscription);


        TemperatureSubscriber seoulSubscriber = new TemperatureSubscriber();

        TemperatureSubscriptionByExecutor seoulSubscription = new TemperatureSubscriptionByExecutor(seoulSubscriber, "Seoul");
        seoulSubscriber.onSubscribe(seoulSubscription);
    }

}
