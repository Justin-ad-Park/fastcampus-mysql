package com.example.reactiveprogramming.L01;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class HelloWorldSample {

    @Test
    void hello_world_test() {
        //데이터 통지하는 생산자
        Flowable<String> flowable = Flowable.just("Hello", "World");

        //통지에 구독자를 등록한다.
        flowable.subscribe(System.out::println);
    }

    @Test
    void interval_test() throws InterruptedException {
        StopWatch sw = JSUtils.startStopWatch();

        JSUtils.printCurrentStopWatch(sw);
        Flowable<Long> flowable = Flowable.intervalRange(0L, 3L, 3, 1, TimeUnit.SECONDS);

        //통지에 구독자를 등록한다.
        flowable.subscribe( data -> {
            System.out.println("Subscribe : " + data);
            JSUtils.printCurrentStopWatch(sw);
        });

        System.out.println("End of the code");
        JSUtils.printCurrentStopWatch(sw);

        Thread.sleep(10000L);
    }

}
