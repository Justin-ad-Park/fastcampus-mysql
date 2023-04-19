package com.example.reactiveprogramming.L01;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class L17_SubscriptionCancelSample {
    @Test
    void cancelSubscription() {
        Flowable.interval(200L, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {
                    private Subscription subscription;
                    private Long startTime;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.startTime = System.currentTimeMillis();
                        this.subscription.request(Long.MAX_VALUE);

                    }

                    @Override
                    public void onNext(Long data) {
                        //0.5초가 지냈으면 구독을 해지하고, 처리를 중단한다.
                        if(System.currentTimeMillis() - startTime > 500) {
                            subscription.cancel();  //구독을 해지한다.
                            System.out.println("구독 해지");
                            return;
                        }

                        System.out.println("Date = " + data);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        JSUtils.sleepNoEx(2000L);
    }


}
