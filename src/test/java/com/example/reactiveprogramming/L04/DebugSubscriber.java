package com.example.reactiveprogramming.L04;

import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class DebugSubscriber<T> extends DisposableSubscriber<T> {
    private String label;

    public DebugSubscriber() {
        super();
        this.label = "";
    }

    public DebugSubscriber(final String label) {
        super();
        this.label = label;
    }

    @Override
    public void onNext(T t) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": " + label + ": " + t);
    }

    @Override
    public void onError(Throwable throwable) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": " + label + ": 에러 = " + throwable);

    }

    @Override
    public void onComplete() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": " + label + ": 완료");

    }
}
