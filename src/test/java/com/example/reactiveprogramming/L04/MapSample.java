package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class MapSample {

    @Test
    void MapSampleTest() {
        System.out.println("시작: " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        Flowable<String> flowable = Flowable.just("A","B","C","D","E")
                .delay(1000L, TimeUnit.MILLISECONDS)
                .map(data -> data.toLowerCase());

        System.out.println("Before Subscribe : " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

        flowable.subscribe(data -> {
            System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + ": " + data);
        });

        JSUtils.sleepNoEx(2000L);
        System.out.println("End of main Thread : " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

    }
}
