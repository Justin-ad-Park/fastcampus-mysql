package com.example.reactiveprogramming.L02;

import com.example.javaLang.generic.basic.JSUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class L11_NormalCounterSample {
    @Test
    void NormalCounterTest() {
        StopWatch stopWatch = JSUtils.startStopWatch();

        int counter = 0;
        for(int i = 0; i < 20_000; i++) {
            JSUtils.sleepNoEx(1);
            counter++;
        }

        System.out.println(counter);    //정확하게 20_000을 센다. 단일 스레드라서 너무 당연하지만...

        JSUtils.stopWatchWithMills(stopWatch);
    }

    @Test
    void ThreadCounterTest() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = JSUtils.startStopWatch();

        final VolatileCounter counter = new VolatileCounter();

        Runnable task = () -> {
            for(int i = 0; i < 5_000; i++) {
                JSUtils.sleepNoEx(1);
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

    /**
     * 아래와 같이 VolatileCounter로 처리해도 read, update 간에 스레드 중첩 처리로 인해
     * 카운트가 정확하게 되지 않는다.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void VolatileCounterTest() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = JSUtils.startStopWatch();

        final VolatileCounter counter = new VolatileCounter();

        Runnable task = () -> {
            for(int i = 0; i < 5_000; i++) {
                JSUtils.sleepNoEx(1);
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

    protected final class NormalCounter {
        private int count;

        protected void increment() {
            count++;
        }

        protected int get() {
            return count;
        }
    }


    protected final class VolatileCounter {
        private volatile int count;     //volatile(휘발성의) : 값에 접근할 때마다 캐시가 아닌 메모리에서 값을 참조한다.

        protected void increment() {
            count++;
        }

        protected int get() {
            return count;
        }
    }
}
