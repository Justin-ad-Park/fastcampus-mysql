package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

public class L13_Counter02_MergeSample {

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

        Flowable<Integer> source1 = Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());


        Flowable<Integer> source2 = Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());


        // merge는 다른 스레드에 있는 여러 개의 Flowable/Observable이라 해도 순차적으로 처리한다.
        Flowable.merge(source1, source2)
                .subscribe(data -> counter.increment(),
                        error -> error.printStackTrace(),
                        () -> System.out.println(JSUtils.getThreadName() + " Counter : " + counter.get())
                );

        JSUtils.sleepNoEx(1000L);
    }
}
