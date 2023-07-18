package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class L29_CompositeDisposableSample {
    @Test
    void CompositeDisposableTest() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(Flowable.range(1, 3)
                .doOnCancel(() -> System.out.println("No.1 canceled"))
                .observeOn(Schedulers.computation())
                .subscribe((data) -> {
                    Thread.sleep(100L);
                    System.out.println("No.1 : " + data);
                })
        );

        compositeDisposable.add(Flowable.range(5, 5)
                .doOnCancel(() -> System.out.println("No.2 canceled"))
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    Thread.sleep(100L);
                    System.out.println("No.2 : " + data);
                })
        );

        try {
            Thread.sleep(400L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        compositeDisposable.dispose();
    }
}
