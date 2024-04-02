package com.example.javaLang.generic.lambdapattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.function.Supplier;

public class CDETest {

    private StopWatch sw;
    private String methodName;
    private boolean useLog = false;

    private CDETest() {
        sw = new StopWatch();
    }

    @BeforeEach
    void startStopWatch() {
        sw.start();
    }

    @AfterEach
    void endStopWatch() {
        sw.stop();

        System.out.println(methodName + " running time : " + sw.getTotalTimeMillis() + " ms");
    }

    String getMessage(String methodName) {
        // 10ms이 걸리는 로직이 있다고 가정함
        try {
            Thread.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return methodName + " 실행시간 : " + System.currentTimeMillis() + " 에러 발생했습니다.";
    }

    void logOut(boolean useLog, String message) {
        if(useLog) System.out.println(message);
    }

    @Test
    void 단순실행테스트() {
        methodName = "단순 반복 실행";

        for(int i = 0; i < 1_000; i ++) {
            logOut(useLog, getMessage(methodName));
        }
    }

    void logOut(boolean useLog, Supplier<String> f) {
        if (useLog) System.out.println(f.get());
    }

    @Test
    void 조건부연기실행() {    //conditional diferred execution
        methodName = "조건부 연기 실행";

        for(int i = 0; i < 1_000; i ++) {
            logOut(useLog, () -> getMessage(methodName));

        }
    }


}
