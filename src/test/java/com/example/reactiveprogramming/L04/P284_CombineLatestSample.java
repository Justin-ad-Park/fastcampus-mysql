package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class P284_CombineLatestSample {

    @Test
    void CombineLatestTest() {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(9);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .map(data -> data + 100L);

        /**
         * 새로운 데이터 통지가 있을 때마다 나머지 flowable의 마지막 값을 조합해서 통지를 한다.
         */
        Flowable<List<Long>> flowableConbine = Flowable.combineLatest(
                flowable1, flowable2,
                (data1, data2) -> Arrays.asList(data1, data2)
        );

        flowableConbine.subscribe(new DebugSubscriber<>());

        JSUtils.sleepNoEx(3500L);

    }
}
