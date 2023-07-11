package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class ConcatMapSample {
    @Test
    void ConcatMapSampleTest() {
        Flowable<String> flowable = Flowable.range(10, 10)
                .concatMap(
                  sourceData -> Flowable.interval(300L, TimeUnit.MILLISECONDS)
                  .take(4)
                  .map(data -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " : " + sourceData + " : " + data)
                );

        flowable.subscribe(data -> System.out.println(data));

        JSUtils.sleepNoEx(5000L);
    }
}
