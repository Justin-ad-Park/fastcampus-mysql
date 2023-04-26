package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L10_FlatMapSample_Origin {
    @Test
    void FlatMapTest() {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .flatMap(data -> {
                    long randomL = JSUtils.getRandom(1000L);
                    data = data + ": " + randomL;

                    return Flowable.just(data).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.stopWatchGetTime(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }

    @Test
    void ConcatMapTest() {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .concatMap(data -> {
                    long randomL = JSUtils.getRandom(1000L);
                    data = data + ": " + randomL;

                    return Flowable.just(data).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.stopWatchGetTime(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }
}
