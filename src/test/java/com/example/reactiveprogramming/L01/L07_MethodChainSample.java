package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L07_MethodChainSample {
    @Test
    void MethodChaining() {
        Flowable<Integer> flowable =
                Flowable.just(1,2,3,4,5,6,7,8,9,10)
                        .filter(i -> i % 2 == 0)
                        .map(i -> i * 100);

        flowable.subscribe(i -> System.out.println("Data = " + i));
    }

    @Test
    void MethodChaining2() {
        Flowable<Long> flowable =
                Flowable.rangeLong(1, 20)
                .filter(l -> l % 2 == 0)
                .map(l -> l * 100);

        flowable.subscribe(println());
    }

    @Test
    void MethodChaining3() throws InterruptedException {
        Flowable<Long> flowable =
                Flowable.intervalRange(1, 20, 3, 1, TimeUnit.SECONDS)
                        .filter(l -> l % 2 == 0)
                        .map(l -> l * 100);

        flowable.subscribe(println());

        Thread.sleep(10000L);
    }

    @NotNull
    private Consumer<Long> println() {
        return System.out::println;
    }

}
