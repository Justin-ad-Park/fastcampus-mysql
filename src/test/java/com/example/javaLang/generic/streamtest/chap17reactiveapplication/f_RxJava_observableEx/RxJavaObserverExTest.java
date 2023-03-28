package com.example.javaLang.generic.streamtest.chap17reactiveapplication.f_RxJava_observableEx;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.e_RxJava_observable.TemperatureObserver;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;

public class RxJavaObserverExTest {
    @Test
    void Test() {
        Observable<TemperatureInfo> observable = CelsiusTemeratureGenerator.getCelsiusTemperatures("New York", "Chicage", "San Francis");

        observable.blockingSubscribe(new TemperatureObserver());

    }
}
