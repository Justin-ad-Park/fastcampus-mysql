package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import org.apache.commons.lang3.time.StopWatch;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

public class L10_FlatMapSample_Refactor01ByFunction {

    @Test
    void FlatMapTest() throws Throwable {
        mapTest(flowableByFlatMap);
    }

    @Test
    void ConcatMapTest() throws Throwable {
        mapTest(flowableByconcatMap);
    }

    @Test
    void ConcatMapEagerTest() throws Throwable {
        mapTest(flowableByconcatMapEager);
    }


    private void mapTest(Function<Flowable<String>, Flowable<String>> flowableMapper) throws Throwable {
        StopWatch sw = JSUtils.startStopWatch();
        Flowable<String> fw = Flowable.just("A", "B", "C", "D", "E");


        Flowable<String> flowable = flowableMapper.apply(fw);


        flowable.subscribe(data -> {
            System.out.println(JSUtils.getThreadName() + ": " + data);
            JSUtils.printCurrentStopWatch(sw);
        });

        JSUtils.sleepNoEx(5000L);
    }

    private Function<Flowable<String>, Flowable<String>> flowableByFlatMap = (fw) -> fw.flatMap(getFlowableDelayByF());
    private Function<Flowable<String>, Flowable<String>> flowableByconcatMap = (fw) -> fw.concatMap(getFlowableDelayByF());
    private Function<Flowable<String>, Flowable<String>> flowableByconcatMapEager = (fw) -> fw.concatMapEager(getFlowableDelayByF());

    @NotNull
    private Function<String, Publisher<? extends String>> getFlowableDelayByF() {
        return data -> {
            long randomL = 500L + JSUtils.getRandom(500L);
            String value = data + ": " + randomL;

            return Flowable.just(value).delay(JSUtils.getRandom(randomL), TimeUnit.MILLISECONDS);
        };
    }

}

