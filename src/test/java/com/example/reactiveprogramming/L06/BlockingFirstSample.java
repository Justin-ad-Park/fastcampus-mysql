package com.example.reactiveprogramming.L06;

import com.example.javaLang.generic.basic.JSUtils;
import com.example.reactiveprogramming.L04.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class BlockingFirstSample {
    @Test
    void blockingFirstTest() {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(3)   //take가 없으면 blockingLast()에서 영구 대기됨
        ;

        long firstResult = flowable.blockingFirst();

        flowable.subscribe(new DebugSubscriber<>());

        Assertions.assertEquals(0L, firstResult);
        System.out.println("BlockResult: " + firstResult);

        long lastResult = flowable.blockingLast();
        System.out.println("LastResult: " + lastResult);

        JSUtils.sleepNoEx(2000L);
    }
}
