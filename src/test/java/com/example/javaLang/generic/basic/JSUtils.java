package com.example.javaLang.generic.basic;

import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class JSUtils {
    public static void sleepNoEx(long millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static StopWatch startStopWatch() {
        StopWatch stopWatch = StopWatch.createStarted();

        return stopWatch;
    }

    public static void stopWatchGetTime(StopWatch stopWatch) {
        System.out.println("Elapsed time : " + stopWatch.getTime(TimeUnit.MILLISECONDS) + " ms");
    }

    public static void stopWatchWithMills(StopWatch stopWatch) {
        stopWatch.stop();
        System.out.println("Elapsed time : " + stopWatch.getTime(TimeUnit.MILLISECONDS) + " ms");
    }

    public static void stopWatch(StopWatch stopWatch, TimeUnit timeUnit) {
        stopWatch.stop();
        System.out.println("Elapsed time : " + stopWatch.getTime(timeUnit));
    }

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static long getRandom(long maxValue) {
        Random rnd = new Random();
        return rnd.nextLong(maxValue);
    }

    public static Supplier<String> getNow = () -> LocalDateTime.now().toLocalTime().toString();
}
