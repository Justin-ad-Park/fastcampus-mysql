package com.example.reactiveprogramming.bestpractice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPerSecondWithGracefullyShutdown {
    private static final int MAX_API_CALLS_PER_SECOND = 5;
    private static final int ONE_SECOND_UNIT_MS = 1000;

    @Test
    void test() {

        /** Given
         * Create a list of 1000 integers
         * Create an iterator from the list
         * */
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            data.add(i);
        }

        Iterator<Integer> dataIterator = data.iterator();

        // Create a ExecutorService(Threads)
        ExecutorService executor = Executors.newFixedThreadPool(MAX_API_CALLS_PER_SECOND);

        while(dataIterator.hasNext()) {
            int value = dataIterator.next();

            // Start the scheduled task
            executor.submit(() -> {
                    System.out.println("Thread call :" + value);
                    call(value);
            });
        }

        System.out.println("Executor.shutdown()");

        // Shut down the executor gracefully
        executor.shutdown();

        try {
            // Wait for the executor to finish all tasks for up to 1 hour
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                // If not all tasks finished, force them to shut down
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // If the main thread was interrupted while waiting, force shutdown
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks completed");
    }


    // Assuming the external API accepts an integer and returns a string
    public void call(int input) {
        try {
            Thread.sleep(ONE_SECOND_UNIT_MS);  //초당 API Call 횟수 초과를 방지하기 위해 1초 대기

            // API Call 호출 응답 시간 강제 생성.
            Long rnd = 100 + getRandom(1000L);
            Thread.sleep(rnd);


            System.out.println(Thread.currentThread().getName()+ " Response for " + input + " wait : " + rnd + "ms" );
        } catch (InterruptedException e) {
            System.out.println("Failed to get a response for " + input);
        }
    }

    public static long getRandom(long maxValue) {
        Random rnd = new Random();
        return rnd.nextLong(maxValue);
    }

}

