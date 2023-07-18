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

public class L11_FlowableSample2Simple {

    @Test
    void flowableTest() throws InterruptedException {
        // 1. 람다 메소드 방식 */
        Flowable<String> flowable = Flowable.create(
                stringFlowable, BackpressureStrategy.BUFFER);

        // 2. 인스턴스 생성 방식 */
        // Flowable<String> flowable = Flowable.create(new StringFlowable(), BackpressureStrategy.BUFFER);
        flowable.observeOn(Schedulers.computation())
                .subscribe(new StringSubscriber());

        Thread.sleep(1000L);
    }

    /* 1. 람다 메소드 정의 */
    FlowableOnSubscribe<String> stringFlowable = (emitter) -> {
        String[] datas = {"Hello, world!", "Hi, RxJava!"};

        for (String data:datas) {
            if(emitter.isCancelled()) return;

            emitter.onNext(data);
        }

        emitter.onComplete();
    };

    /* 2. 클래스 정의 */
    class StringFlowable implements FlowableOnSubscribe<String> {
        @Override
        public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
            String[] datas = {"Hello, world!", "Hi, RxJava!"};

            for (String data:datas) {
                if(emitter.isCancelled()) return;

                emitter.onNext(data);
            }

            emitter.onComplete();
        }
    };


    class StringSubscriber implements Subscriber<String> {
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
    }
}
