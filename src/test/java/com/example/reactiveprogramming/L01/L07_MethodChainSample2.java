package com.example.reactiveprogramming.L01;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class L07_MethodChainSample2 {
    private final Executor executor =
            Executors.newFixedThreadPool(10,
                    r -> {
                        Thread t = new Thread(r);
                        t.setDaemon(true);  // 데몬 스레드로 만들면, 프로그램 종료 시 스레드가 함께 종료된다.
                        return t;
                    }
            );

    @Test
    void MethodChaining3() throws InterruptedException {
        //1. Flowable을 만든다.
        Flowable<Long> flowable =
                Flowable.intervalRange(1, 7, 3, 1, TimeUnit.SECONDS)
                        .filter(l -> l % 2 == 0)
                        .map(l -> l * 100);

        //1-1. Flowable의 통지를 처리할 subscriber를 등록한다.
        flowable.subscribe(println);

        // 2. 메인스레드와 분리된 독립 스레드를 만들어 비동기로 실행한다.
        CompletableFuture.runAsync(      //내부의 호출을 비동기로 처리한다.
                countOtherThread, executor);

        // 메인 스레드의 실행을 표시한다.
        System.out.println("Before main thread sleep");
        Thread.sleep(13000L);
        System.out.println("After main thread sleep");

        System.out.println("End of Main thread");
    }

    private Runnable countOtherThread = () -> {
            IntStream.rangeClosed(0, 20).forEach(i -> {
                Shop.delay(1);
                System.out.println("Other thread count - " + i);
            });

            System.out.println("End of other thread");
        };

    @NotNull
    private Consumer<Long> println = value -> System.out.println("Subscribed Data = " + value);

}
