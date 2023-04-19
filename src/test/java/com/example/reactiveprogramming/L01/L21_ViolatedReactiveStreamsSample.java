package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L21_ViolatedReactiveStreamsSample {

    @Test
    void TestViolatedReactiveStreams() {
        Flowable.range(1,3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("OnSubscribe: Start");
                        s.request(Long.MAX_VALUE);
                        System.out.println("OnSubscribe: End");
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println(data);

                    }

                    @Override
                    public void onError(Throwable t) {


                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");

                    }
                });
    }
}
