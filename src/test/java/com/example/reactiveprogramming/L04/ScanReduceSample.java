package com.example.reactiveprogramming.L04;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class ScanReduceSample {

    @Test
    void reduceTest() {
        Single<Integer> single = getFlowable1to10
                .reduce(0, sum);

        single.subscribe(getResult("Singel: "));
    }

    @Test
    void scanTest() {
        Flowable<Integer> flowable = getFlowable1to10
                .scan(0, sum);

        flowable.subscribe(getResult("Flowable: "));
    }

    Flowable<Integer> getFlowable1to10 = Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    BiFunction<Integer, Integer, Integer> sum = (sum, data) -> sum + data;

    @NotNull
    private Consumer<Integer> getResult(String header) {
        return data -> System.out.println(header + data);
    }


}
