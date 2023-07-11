package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class L22_MissingBackpressureFlowableSample {

    /**
     * 역압(Backpressure)이 발생하도록
     * Flowable에서는 20ms로 데이터를 생성하고,
     * Subscriver는 500ms로 데이터를 처리시킨다.
     *
     * 기본 버퍼는 128건 이기 때문에 약 3초 후에 128건을 초과한 데이터가 쌓여 역압이 발생한다.
     */
    @Test
    void MainTest() {
        Flowable<Long> flowable = Flowable.interval(20L, TimeUnit.MILLISECONDS)
                .doOnNext(value -> System.out.println("emit: " + value));

        observeOn(flowable, Long.MAX_VALUE);

        JSUtils.sleepNoEx(5_000L);
    }

    @Test
    void MainTest2() {
        Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MILLISECONDS)
                .doOnNext(value -> System.out.println("emit: " + value));

        flowable = flowable.onBackpressureDrop();

        observeOn(flowable, 2L);

        JSUtils.sleepNoEx(5_000L);
    }

    private void observeOn(Flowable<Long> flowable, Long capacity) {

        Subscriber<Long> subscriber = getSubscriber(capacity);

        flowable.observeOn(Schedulers.computation())
                .subscribe(subscriber);
    }

    @NotNull
    private Subscriber<Long> getSubscriber(Long c) {
        return new Subscriber<Long>() {
            private Subscription subscription;
            private Long capacity;

            @Override
            public void onSubscribe(Subscription s) {
                capacity = c;
                subscription = s;
                //무제한으로 데이터를 통지한다.
                s.request(capacity);

            }

            @Override
            public void onNext(Long value) {
                //1000ms 지연 후 처리한다.
                System.out.println("Waiting...");
                JSUtils.sleepNoEx(500L);

                System.out.println("received: " + value);

                subscription.request(capacity);

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("에러=" + throwable);

            }

            @Override
            public void onComplete() {
                System.out.println("종료");
            }
        };
    }
}
