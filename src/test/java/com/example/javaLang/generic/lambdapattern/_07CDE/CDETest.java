package com.example.javaLang.generic.lambdapattern._07CDE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.function.Supplier;

/**
 * <Pre>
 * 조건부 연기 로직(Conditional Delayed Execution)
 *
 * 전통적인 메서드 파라미터 자리에 메서드를 넣는 경우 메서드의 실행 결과가 대입되는데 비해
 * 함수형 인터페이스를 파라미터로 넣는 경우에는 내부 로직에서 함수 호출이 된 경우에만 메서드(함수형 인터페이스의 구현체)가 실행된다.
 *
 * 이로 인해 내부 로직에서 호출되지 않을 가능성이 높은 메서드의 경우에
 * 함수형 인터페이스로 메서드를 넘기면 런타임 성능이 개선된다.
 * </Pre>
 */


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
        // 2ms이 걸리는 로직이 있다고 가정함
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
