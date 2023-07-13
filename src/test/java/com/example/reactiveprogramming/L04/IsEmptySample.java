package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class IsEmptySample {
    @Test
    void emptyTest() {
        Flowable<Long> flowable = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3);    //3건까지 통지(0,1,2)

        Single<Boolean> single = flowable
                .filter(data -> data >= 4)  //4 이상 통지
                .isEmpty(); //filter로 인해 통지 대상이 없음. 즉, true

        flowable.subscribe(data -> System.out.println("Data: " + data));    //flowable은 0,1,2 까지 통지
        single.subscribe(data -> System.out.println("Is Empty : " + data) );

        JSUtils.sleepNoEx(4000L);

    }
}
