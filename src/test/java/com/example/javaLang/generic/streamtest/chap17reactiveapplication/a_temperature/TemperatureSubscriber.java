package com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature;


import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TemperatureSubscriber implements Subscriber<TemperatureInfo> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(TemperatureInfo tempInfo) {
        System.out.println(" [Subscriber]받은 정보 : " + tempInfo);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());

    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
