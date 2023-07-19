package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L10_FlatMapSample_Origin {

    /**
     * 새로운 Flowable/Observable 생성. 별도의 스레드에서 처리. 통지 순서 달라질 수 있음
     */
    @Test
    void FlatMapTest() {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .flatMap(data -> {
                    long randomL = 500L + JSUtils.getRandom(500L);
                    String value = data + ": " + randomL;

                    return Flowable.just(value).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.printCurrentStopWatch(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }

    /**
     * 다른 스레드에서 처리하지만, 연쇄적(순서대로)으로 통지.
     */
    @Test
    void ConcatMapTest() {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .concatMap(data -> {
                    long randomL = 500L + JSUtils.getRandom(500L);
                    String value = data + ": " + randomL;

                    return Flowable.just(value).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.printCurrentStopWatch(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }

    /**
     * 여러 스레드(동일 처리도 함)에서 처리. 순서대로 통지. 각 스레드 지연없이 실행
     */
    @Test
    void ConcatMapEagerTest() {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .concatMapEager(data -> {
                    long randomL = 500L + JSUtils.getRandom(500L);
                    String value = data + ": " + randomL;

                    return Flowable.just(value).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.printCurrentStopWatch(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }

}
