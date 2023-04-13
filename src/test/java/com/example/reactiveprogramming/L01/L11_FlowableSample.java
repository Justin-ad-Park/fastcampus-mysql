package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * <img src="img/IMG_4292.JPG">
 * <pre>
 *  1.      Subscriber가 Flowable을 구독하고 Flowable이 처리를 시작한다.
 *  2. Flowable이 Subscription을 생성한다.
 *  3. Flowable의 구독 시작을 Subscriber에 통지(OnSubscribe)하고 이때 생성된 Subscription을 전달한다.
 *  4.      Subscriber는 전달받은 Subscription의 request 메서드로 데이터 1건을 통지하게 요청한다.
 *  5. Flowable이 Subscription에 요청된 데이터 개수를 확인하고, 문자열 'Hello, world!"를 통지한다.
 *  6.      Subscriber가 데이터를 받아 'Hello, World!'를 출력한다.
 *  7.      Subscriber가 Subscription의 request 메서드로 데이터 1건을 요청한다.
 *  8. Flowable이 Subscription에 요청된 데이터 개수를 확인해 문자열 'Hi, RxJava!'를 통지한다.
 *  9.      Subscriber가 데이터를 받아 'Hi, RxJava!'를 출력한다.
 *  10. 모든 데이터를 통지한 후 Flowable이 처리를 완료(onComplete)했음을 통지한다.
 *  11.     Subscriber가 완료 통지를 받아 '완료'라고 출력한다.
 * </pre>
 */
public class L11_FlowableSample {

    @Test
    void flowableTest() throws InterruptedException {
        // 1.
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter)
                    throws Throwable {  // 2.

                String[] datas = {"Hello, world!", "Hi, RxJava!"};

                for (String data:datas) {
                    // 3. 구독이 해지되면 처리를 중단한다.
                    if(emitter.isCancelled()) return;

                    // 4. 데이터를 통지한다.
                    emitter.onNext(data);
                }

                // 5. 완료를 통지한다.
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);    // 6. 초과된 데이터는 버퍼링한다.


        /**
         * [배압 전략]
         * MISSING	BackpressureStrategy.MISSING	배압 전략을 구현하지 않음
         * ERROR	BackpressureStrategy.ERROR	소비 속도가 발행 속도를 따라가지 못하는 경우 MissingBackpressureException 발생
         * BUFFER	BackpressureStrategy.BUFFER	데이터를 소비할 때까지 데이터를 버퍼에 넣어둠. 무한한 크기의 큐이지만 OOME(Out of memory exception)이 발생할 수 있음.
         * DROP	    BackpressureStrategy.DROP	소비 속도가 발행 속도를 따라가지 못하는 경우 발행된 데이터를 모두 버림
         * LATEST	BackpressureStrategy.LATEST	구독자가 데이터를 받을 준비가 될 때까지 최신 데이터만 유지하고 나머지는 버림
         */

        flowable
            .observeOn(Schedulers.computation())
            .subscribe(new Subscriber<String>() {
                private Subscription subscription;
                @Override
                public void onSubscribe(Subscription s) {
                    subscription = s;
                    subscription.request(1L);
                }

                @Override
                public void onNext(String data) {
                    //실행 중인스레드 이름을 얻는다.
                    String threadName = Thread.currentThread().getName();

                    //스레드 이름과 받은 데이터를 출력한다.
                    System.out.println(threadName + " : " + data);

                    //다음에 받을 데이티 개수를 요청한다.
                    this.subscription.request(1L);
                }

                @Override
                public void onError(Throwable ex) {//실행 중인스레드 이름을 얻는다.
                    ex.printStackTrace();

                }

                @Override
                public void onComplete() {
                    //실행 중인스레드 이름을 얻는다.
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " : 완료");

                }
            });

        Thread.sleep(1000L);


    }
}
