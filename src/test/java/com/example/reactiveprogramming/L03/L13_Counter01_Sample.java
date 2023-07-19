package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L13_Counter01_Sample {

    protected final class VolatileCounter {
        private volatile int count;     //volatile(휘발성의) : 값에 접근할 때마다 캐시가 아닌 메모리에서 값을 참조한다.

        protected void increment() {
            count++;
        }

        protected int get() {
            return count;
        }
    }

    private Subscription subscription;

    @Test
    void CounterTest() {
        final VolatileCounter counter = new VolatileCounter();


        Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe( data -> counter.increment(),
                        error -> error.printStackTrace(),
                        () -> System.out.println(JSUtils.getThreadName() + " Counter : " + counter.get())
                );

        Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        counter.increment();
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(JSUtils.getThreadName() + " Counter : " + counter.get());
                    }
                });

        JSUtils.sleepNoEx(1000L);
    }

    private void flowableCounter(VolatileCounter counter) {
        Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //doNothing
                    }

                    @Override
                    public void onNext(Integer integer) {
                        counter.increment();
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Counter : " + counter.get());
                    }
                });
    }
}
