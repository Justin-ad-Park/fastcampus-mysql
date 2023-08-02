package com.example.reactiveprogramming.L06;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class P377_TestSchedulerSample {
    @Test
    void testSchedulerTest() {
        StopWatch st = JSUtils.startStopWatch();

        TestScheduler testScheduler = new TestScheduler();

        Flowable<Long> flowable = Flowable.interval(500L, TimeUnit.MILLISECONDS, testScheduler);

        //구독을 시작한다.
        TestSubscriber<Long> result = flowable.test();

        printResult(result);
        result.assertEmpty();   //데이터가 없으면 통과

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);   //500밀리초 진행
        printResult(result);

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);   //500밀리초 진행
        printResult(result);
        result.assertValues(0L, 1L);

        testScheduler.advanceTimeTo(2000L, TimeUnit.MILLISECONDS);

        printResult(result);
        result.assertValues(0L, 1L, 2L, 3L);

        testScheduler.advanceTimeBy(300L, TimeUnit.MILLISECONDS);   //500밀리초 진행

        System.out.println("TestScheduler#now=" + testScheduler.now(TimeUnit.MILLISECONDS));

        JSUtils.printCurrentStopWatch(st);

    }

    private void printResult(TestSubscriber<Long> result) {
        System.out.println("Data=" + result.values());
    }
}
