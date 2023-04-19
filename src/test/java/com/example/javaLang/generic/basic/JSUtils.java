package com.example.javaLang.generic.basic;

import java.time.LocalDateTime;
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

    public static Supplier<String> getNow = () -> LocalDateTime.now().toLocalTime().toString();
}
