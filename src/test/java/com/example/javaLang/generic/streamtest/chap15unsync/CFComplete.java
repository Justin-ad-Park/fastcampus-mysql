package com.example.javaLang.generic.streamtest.chap15unsync;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture
 */
public class CFComplete {
    public static int add5(int value) throws InterruptedException {
        System.out.println("Add5");
        Thread.sleep(500);
        return value + 3;
    }

    public static int dubl(int value) throws InterruptedException {
        System.out.println("dubl");
        Thread.sleep(200);
        return value * 2;
    }

    @Test
    void procedure_Test() throws InterruptedException {
        System.out.println("ProcedureTest");

        int x = 2;
        System.out.println(add5(x) + dubl(x));


    }

    @Test
    void cfComplete_test() throws ExecutionException, InterruptedException {
        System.out.println("cfComplete_test");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 2;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(add5(x)));

        int b = dubl(x);
        System.out.println(a.get() + b);

        executorService.shutdown();

    }

    /**
     * 블록 문제없이 thread 분산 처리 방법
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void cfCombine_Test() throws ExecutionException, InterruptedException {
        System.out.println("cfCombine_Test");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 2;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);

        executorService.submit(() -> a.complete(add5(x)));
        executorService.submit(() -> b.complete(dubl(x)));

        System.out.println(c.get());
        executorService.shutdown();
    }

}
