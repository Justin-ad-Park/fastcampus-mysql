package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import org.apache.commons.lang3.time.StopWatch;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class L10_FlatMapSample_Refactor03ByFunction2 {

    @Test
    void flatMapTest() throws Throwable {
        flowableBuilder(f -> f.flatMap(justFlowableWithDelay) );

        flowableBuilder(f -> f.concatMap(justFlowableWithDelay) );

        flowableBuilder(f -> f.concatMapEager(justFlowableWithDelay) );
    }

    private void flowableBuilder(Function<Flowable<String>, Flowable<String>> fwMapper) throws Throwable {
        StopWatch sw = JSUtils.startStopWatch();

        Flowable<String> fw = Flowable.just("A", "B", "C", "D", "E");

        fw = fwMapper.apply(fw);

        fw.subscribe(data -> {
            JSUtils.printCurrentStopWatch(sw);
            System.out.println(data);
        });

        JSUtils.sleepNoEx(5000L);

    }

    @NotNull
    private Function<String, Flowable<? extends String>> justFlowableWithDelay =data -> {
        Long delay = 500L + JSUtils.getRandom(500L);
        String value = JSUtils.getThreadName() + ": " + data + ": " + delay;

        return Flowable.just(value).delay(delay, TimeUnit.MILLISECONDS);
    };
}

