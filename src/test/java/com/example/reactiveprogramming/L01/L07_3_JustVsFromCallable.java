package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class L07_3_JustVsFromCallable {
    @Test
    void test() throws InterruptedException {
        System.out.println("시작: " + LocalDateTime.now());

        Flowable<LocalDateTime> flowableJust = Flowable.just(LocalDateTime.now());  //subscriber와 관계없이 just 내부의 메소드가 실행되어 데이터가 생성된다.
        Flowable<LocalDateTime> flowableFromCallable = Flowable.fromCallable(LocalDateTime::now);   //subscriber에서 subscribe하는 시점에 메소드가 호출된다.

        Thread.sleep(3000L);

        flowableJust.subscribe(printData);
        flowableFromCallable.subscribe(printData);

    }

    @NotNull
     Consumer<LocalDateTime> printData = data -> System.out.println(Thread.currentThread().getName() + ": " + data);

}
