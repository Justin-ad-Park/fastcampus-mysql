package com.example.lambdapattern._07CDE;

import com.example.aop.stopwatch.StopWatchOn;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CDEexample {

    final boolean useLog = false;

    String getMessage(String methodName) {
        // 2ms이 걸리는 로직이 있다고 가정함
        try {
            Thread.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return methodName + " 실행시간 : " + System.currentTimeMillis() + " 에러 발생했습니다.";
    }

    @StopWatchOn(watchName = "일반적인 호출 메서드")
    public void normalMethod() {
        String methodName = "단순 반복 실행";

        for (int i = 0; i < 1_000; i++) {
            logOut(useLog, getMessage(methodName));
        }


    }

    @StopWatchOn(watchName = "조건부 연기 메서드")
    public void CDEMethod() {
        String methodName = "조건부 연기 실행";

        for (int i = 0; i < 1_000; i++) {
            logOut(useLog, () -> getMessage(methodName));

        }

    }

    void logOut(boolean useLog, String message) {
        if (useLog) System.out.println(message);
    }

    void logOut(boolean useLog, Supplier<String> f) {
            if (useLog) System.out.println(f.get());
        }

}
