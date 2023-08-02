package com.example.reactiveprogramming.L04;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class P298_ScanReduceSample {

    /**
     * reduce : 계산된 최종 결과만을 통지하는 연산자.
     * 초깃값을 받으면 single, 받지 않으면 maybe를 반환값으로 생성한다.
     */
    @Test
    void reduceTest() {
        Single<Integer> single = getFlowable1to10
                .reduce(0, sum);

        single.subscribe(getResult("Singel: "));
    }

    /**
     * scan : 함수형 인터페이스를 반복 호출해서 집계하는 것은 reduce와 같은데,
     * scan은 각각의 연산 결과를 통지한다.
     * 초기값이 있으면 초기값을 첫 번째 데이터로 통지(Flowable / Observable)하고,
     * 초기값이 없으면 첫 번째 연산 결과부터 통지한다.
     */
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
