package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimerSample {

    @Test
    void timerSampleTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

        System.out.println("시작 시간: " + LocalTime.now().format(formatter));

        Flowable<Long> flowable = Flowable.timer(1000L, TimeUnit.MILLISECONDS);

        flowable.subscribe(data -> {
            String time = LocalTime.now().format(formatter);
            System.out.println(Thread.currentThread().getName() + ": " + time + ": Data=" + data);
        });

        JSUtils.sleepNoEx(2000L);
    }
}
