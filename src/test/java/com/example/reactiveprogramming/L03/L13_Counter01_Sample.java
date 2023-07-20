package com.example.reactiveprogramming.L03;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L13_Counter01_Sample {

    protected final class VolatileCounter {
        /**
         * 미국식 [ ˈvɑːlətl ] 영국식 [ ˈvɒlətaɪl ]
         * volatile(휘발성의) : 값에 접근할 때마다 캐시가 아닌 메모리에서 값을 참조한다.
         *
         * 메모리 방식으로 참조를 사용해도 값을 읽을 때와
         * 값을 쓸 때의 타이밍 차이로 인해 다중 스레드에서는 문제가 발생할 수 있다.
         */
        private volatile int count;

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

        /**
         * 아래와 같이 두 개 스레드가 동시에 값에 쓰기를 시도하면
         * 의도치 않은 side-effect이 발생함
         */
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
