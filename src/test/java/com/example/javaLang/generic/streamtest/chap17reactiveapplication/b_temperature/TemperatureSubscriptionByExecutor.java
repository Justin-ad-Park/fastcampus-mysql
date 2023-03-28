package com.example.javaLang.generic.streamtest.chap17reactiveapplication.b_temperature;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class TemperatureSubscriptionByExecutor implements Flow.Subscription {

    private final Flow.Subscriber<? super TemperatureInfo> subscriber;
    private final String town;

    public TemperatureSubscriptionByExecutor(final Flow.Subscriber<? super TemperatureInfo> subscriber, final String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void request(long n) {
        executor.submit(() -> {
            for(long i = 0L; i < n; i++) {  //Subscriber가 만든 요청을 한 개씩 반복
                try {
                    subscriber.onNext(TemperatureInfo.fetch(town)); // 현재 온도를 Subscriber로 전달
                } catch (Exception ex) {
                    subscriber.onError(ex); // 온도 가져오기를 실패하면 Subscriber로 에러를 전달
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        subscriber.onComplete();    //구독이 취소되면 완료 신호를 Subscriber로 전달
    }
}
