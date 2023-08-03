package com.example.reactiveprogramming.bestpractice;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DelayMainThreadUntilCompleteProcess {
    public static void main(String[] args) {
        AtomicBoolean completed = new AtomicBoolean(false);

        Flowable.interval(1, TimeUnit.SECONDS)
                .take(10)  // 10초 동안만 수행하게 함
                .observeOn(Schedulers.io())
                .doOnComplete(() -> {
                    System.out.println("On Complete");
                    completed.set(true);
                })
                .subscribe(System.out::println);

        while (!completed.get()) {
            try {
                System.out.println("Waiting...main thread.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Flowable 처리 완료. 메인 스레드 종료.");
    }
}