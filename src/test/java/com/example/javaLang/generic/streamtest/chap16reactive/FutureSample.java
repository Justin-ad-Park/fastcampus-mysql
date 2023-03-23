package com.example.javaLang.generic.streamtest.chap16reactive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class FutureSample {
    @Test
    void FutureTest() throws InterruptedException, ExecutionException, TimeoutException {
        executeWithFuture(10);
    }

    @Test
    void FutureTest_Timeout() {
        Assertions.assertThrows(TimeoutException.class, () -> executeWithFuture(2));

    }

    private void executeWithFuture(int timesoutSecs) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();
            }
        });

        doSomethingElse();

        Double result = future.get(timesoutSecs, TimeUnit.SECONDS);
        System.out.println("==Complete== : " + result);

    }

    private void doSomethingElse() throws InterruptedException {
        int repetitions = 6;

        for(int i = 0; i < repetitions; i++) {
            Thread.sleep(500L);
            System.out.println("[DoSomethingElse] " + i + "/" + repetitions);
        }

    }

    private Double doSomeLongComputation() throws InterruptedException {
        int repetitions = 10;
        
        for(int i = 0; i < repetitions; i++) {
            Thread.sleep(1000L);
            System.out.println("[DoSomeLongComputation] " + i + "/" + repetitions);
        }

        return 0.01;
    }


}
