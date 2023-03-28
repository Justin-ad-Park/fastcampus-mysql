package com.example.javaLang.generic.streamtest.chap17reactiveapplication.b_temperature;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureSubscriber;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow.Publisher;

public class TemperatureTest {


    @Test
    void easyMainCodeTest() {
        TemperatureSubscriber subscriber = new TemperatureSubscriber();
        TemperatureSubscriptionByExecutor subscription = new TemperatureSubscriptionByExecutor(subscriber, "New York");

        subscriber.onSubscribe(subscription);
    }

    /**
     * 아래 코드에서는 "New York" 지역의 기온을 가져오기 위해 Lambda 함수를 사용하여
     * TemperatureSubscriber를 구독합니다.
     *
     * getTemperatures 함수는 Lambda 함수를 사용하여 Publisher를 만들고,
     * TemperatureSubscription을 사용하여 Subscriber에게 onSubscribe 이벤트를 보냅니다.
     * 이러한 방식으로 온도 정보를 가져올 수 있습니다.
     */
    @Test
    void testCreateMethodByLambda() {
        // "New York"의 기온을 가져오기 위해 Lambda 함수를 사용하여 TemperatureSubscriber를 구독합니다.
        getTemperatures("New York").subscribe(new TemperatureSubscriber());
    }

    // Lambda 함수를 사용하여 기온을 가져오는 Publisher를 만듭니다.
    private static Publisher<TemperatureInfo> getTemperatures(String town) {
        // TemperatureSubscription을 사용하여 Subscriber에게 onSubscribe 이벤트를 보냅니다.
        return subscriber -> subscriber.onSubscribe(new TemperatureSubscriptionByExecutor(subscriber, town));
    }

    @Test
    void createMethodByLambdaTest() {
        getTemperaturesByLambda("New York").subscribe(new TemperatureSubscriber());
    }

    private static Publisher<TemperatureInfo> getTemperaturesByLambda(String town) {
        return subscriber -> subscriber.onSubscribe(new TemperatureSubscriptionByExecutor(subscriber, town));
    }


}
