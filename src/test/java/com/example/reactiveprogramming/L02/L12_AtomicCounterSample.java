package com.example.reactiveprogramming.L02;

import com.example.javaLang.generic.basic.JSUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1~20,000을 카운트하는 로직을 스레드 2개로 나눠서 처리한다.
 */
public class L12_AtomicCounterSample {
    @Test
    void atomicCounterTest() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = JSUtils.startStopWatch();

        //스레드에도 데이터를 안전하게 업데이트 및 조회할 수 있는 AtomicCounter 객체
        AtomicCounter counter = new AtomicCounter();

        //10,000번 계산하는 비동기 처리 작업 준비
        Runnable task = () -> {
            for(int i = 0; i < 5_000; i++) {
                JSUtils.sleepNoEx(1);   //  DoSomeThing의 역할을 1ms 지연으로 대체
                counter.increment();
            }
        };

        //비동기 처리 작업 생성 준비
        ExecutorService executorService = Executors.newCachedThreadPool();

        //새로운 스레드로 시작
        Future<Boolean> future1 = executorService.submit(task, true);
        Future<Boolean> future2 = executorService.submit(task, true);
        Future<Boolean> future3 = executorService.submit(task, true);
        Future<Boolean> future4 = executorService.submit(task, true);

        if(future1.get() && future2.get() && future3.get() && future4.get() ) {
            System.out.println(counter.get());
        } else {
            System.out.println("실패");
        }

        executorService.shutdown();

        JSUtils.stopWatchWithMills(stopWatch);

    }

    protected final class AtomicCounter {


        private final AtomicInteger count = new AtomicInteger(0);

        protected void increment() {
            count.incrementAndGet();
        }

        protected int get() {
            return count.get();
        }
    }
}
