package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

public class FlatMapSample {

    @Test
    void FlatMapSampleTest() {

        Flowable<String> flowable = Flowable.just("A", "B", "", "C")
                .flatMap(data -> {
                    if ("".equals(data)) {
                        return Flowable.empty();
                    } else {
                        return Flowable.just(data, data.toLowerCase());
                    }
                });

        flowable.subscribe(data -> System.out.println(data));
    }

    @Test
    void FlatMapSample3() {
        Flowable<Integer> original = Flowable.just(1,2,0,4,5)
                .map(data -> 10/data);

        Flowable<Integer> flowable = original.flatMap(
                onNextMapper(),
                onErrorMapper(),
                onCompleteSupplier()
        );

        flowable.subscribe(data -> System.out.println(data));

    }


    @Test
    void FlatMapSample3_ExtFunction() {
        Flowable<Integer> original = Flowable.just(1,2,0,4,5)
                .map(data -> 10/data);

        Flowable<Integer> flowable = original.flatMap(
                onNextMapper(),
                onErrorMapper(),
                onCompleteSupplier()
        );

        flowable.subscribe(data -> System.out.println(data));

    }


    /**
     * 함수형 메소드
     * @return
     */
    @NotNull
    private Function<Integer, Publisher<? extends Integer>> onNextMapper() {
        return data -> Flowable.just(data);
    }

    @NotNull
    private Function<Throwable, Publisher<? extends Integer>> onErrorMapper() {
        return error -> Flowable.just(-1);
    }

    @NotNull
    private Supplier<Publisher<? extends Integer>> onCompleteSupplier() {
        return () -> Flowable.just(100);
    }


    @Test
    void FlatMapSample4_ExtFunctionToLambda() {
        Flowable<Integer> original = Flowable.just(1,2,3,4,5)
                .map(data -> 20/data);

        Flowable<Integer> flowable = original.flatMap(
                onNextMapper1,
                onErrorMapper1,
                onCompleteSupplier1
        );

        flowable.subscribe(data -> System.out.println(data));

        JSUtils.sleepNoEx(1000L);

    }


    /**
     * 람다식
     */
    private Function<Integer, Publisher<? extends Integer>> onNextMapper1 = data -> Flowable.just(data);
    private Function<Throwable, Publisher<? extends Integer>> onErrorMapper1 = error -> Flowable.just(-1);
    Supplier<Publisher<? extends Integer>> onCompleteSupplier1 = () -> Flowable.just(100);
}
