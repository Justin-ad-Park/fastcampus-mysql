package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class P215_BufferSample {
    @Test
    void BufferSampleTest() {
        Flowable<List<Long>> flowable =
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(3);

        flowable.subscribe(new DebugSubscriber<>());

        JSUtils.sleepNoEx(3000L);
    }
}
