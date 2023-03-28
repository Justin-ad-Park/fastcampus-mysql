package com.example.javaLang.generic.streamtest.chap17reactiveapplication.e_RxJava_observable;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;

public class RxObserverTest {

    @Test
    void Test() {
        Observable<TemperatureInfo> observable = TemperatureGenerator.getTemperature("New York");
        observable.blockingSubscribe(new TemperatureObserver());
    }
}
