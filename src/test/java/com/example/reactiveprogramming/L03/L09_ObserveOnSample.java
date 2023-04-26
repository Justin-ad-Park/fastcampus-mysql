package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L09_ObserveOnSample {
    @Test
    void ObserveOnTest() {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .onBackpressureDrop(); //BackpressureMode.DROP을 설정했을 때와 동일하게 동작한다.

        flowable.observeOn(Schedulers.computation(), false, 1)  //에러 발생 시 즉시 통지, 데이터 버퍼 사이즈 1
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                        System.out.println(Thread.currentThread().getName() + " : " + data);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        JSUtils.sleepNoEx(7_000L);
    }
}
