package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class P207_ConcatMapSample {

    /**
        데이터를 받은 순서대로 변환해서 Flowable/Observable로 통지
        FlatMap과 차이는 ConcatMap은 순서처리는 보장하지만, 처리 성능에 영향이 있을 수 있음
     */
    @Test
    void ConcatMapSampleTest() {
        Flowable<String> flowable = Flowable.range(10, 10)
                .concatMap(  /* 데이터를 받은 순서대로 변환해 Flowable/Observable의 데이터를 통지 */
                  sourceData -> Flowable.interval(300L, TimeUnit.MILLISECONDS)
                  .take(4)
                  .map(data -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " : " + sourceData + " : " + data)
                );

        flowable.subscribe(data -> System.out.println(data));

        JSUtils.sleepNoEx(5000L);
    }
}
