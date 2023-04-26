package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L04_SyncSlowerSample {

    @Test
    void syncSlowerTest() {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .doAfterNext(data -> System.out.println("emit: " + System.currentTimeMillis() + "ms : " + data))
                .subscribe(data -> JSUtils.sleepNoEx(2000L));

        JSUtils.sleepNoEx(7000L);
    }

    @Test
    void L05_SyncFasterTest() {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .doAfterNext(data -> System.out.println("emit: " + System.currentTimeMillis() + "ms : " + data))
                .subscribe(data -> JSUtils.sleepNoEx(500L));

        JSUtils.sleepNoEx(6000L);

    }

    @Test
    void L06_MainThreadTest() {
        final String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": =시작=");

        Flowable.just(1, 2, 3)
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }
                });

        System.out.println(threadName + ": =완료=");
    }

    @Test
    void L07_NonMainThreadTest() {
        final String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": =시작=");

        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }
                });

        System.out.println(threadName + ": =완료=");

        JSUtils.sleepNoEx(1000L);
    }

}
