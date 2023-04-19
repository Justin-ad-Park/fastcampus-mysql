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

import static com.example.javaLang.generic.basic.JSUtils.sleepNoEx;

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
        /** 1.Flowable과 Observable의 차이
         *  Observable : 배압 조절을 하지 못함. 즉, Subscription에 통지가 발생하면 무조건 Observer에 통지됨
         *  Flowable : 배압 조절이 가능. Flowable은 메시지의 처리 방법(버퍼사용, 버림, 마지막만 유지 등)을 제어하고, Subscriber 측에서 배압을 관리
         *
         * Observable을 사용해야하는 경우
         * 1,000건 미만의 데이터 흐름이 발생하는 경우
         * 적은 데이터 소스만을 활용하여 OutOfMemoryException이 발생할 확률이 적은 경우
         * 마우스 이벤트나 터치 이벤트와 같은 GUI 프로그래밍을 하는 경우 (초당 1,000회 이하의 이벤트는 Observable의 sample()이나 debounce()로 핸들링 가능)
         * 동기적인 프로그래밍이 필요하지만 플랫폼에서 Java Streams을 지원하지 않는 경우
         *
         * Flowable을 사용해야하는 경우
         * 10,000건 이상의 데이터 흐름이 발생하는 경우
         * 디스크에서 파일을 읽는 경우 (기본적으로 Blocking/Pull-based 방식)
         * JDBC에서 데이터베이스를 읽는 경우 (기본적으로 Blocking/Pull-based 방식)
         * 네트워크 IO 실행 시
         * Blocking/Pull-based 방식을 사용하고 있는데 나중에 Non-Blocking 방식의 Reactive API/드라이버에서 데이터를 가져올 일이 있는 경우
          */
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {  // 2.

                String[] datas = {"Hello, world!", "Hi, RxJava!", "One More Time!"};

                for (String data:datas) {
                    // 3. 구독이 해지되면 처리를 중단한다.
                    if(emitter.isCancelled()) return;

                    // 4. 데이터를 통지한다. Null 허용 안됨
                    System.out.println("subscribe : 데이터 통지 : " + data);
                    emitter.onNext(data);
                }

                // 5. 완료를 통지한다. 완료 통지 이후에 어떤 통지도 하면 안됨
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);    // 6. 배압옵션 : 초과된 데이터는 버퍼링한다.
                            /** Subscriber의 데이터 처리 속도보다 데이터의 생성 속도가 빠를 때
                             * 대기하는 데이터를 버퍼링해 통지할 수 있을 때까지 유지하게 함

        /**
         * [BackpressureStrategy - 배압 전략]
         * MISSING	BackpressureStrategy.MISSING	배압 전략을 구현하지 않음
         * ERROR	BackpressureStrategy.ERROR	소비 속도가 발행 속도를 따라가지 못하는 경우 MissingBackpressureException 발생
         * BUFFER	BackpressureStrategy.BUFFER	데이터를 소비할 때까지 데이터를 버퍼에 넣어둠. 무한한 크기의 큐이지만 OOME(Out of memory exception)이 발생할 수 있음.
         * DROP	    BackpressureStrategy.DROP	소비 속도가 발행 속도를 따라가지 못하는 경우 발행된 데이터를 모두 버림
         * LATEST	BackpressureStrategy.LATEST	구독자가 데이터를 받을 준비가 될 때까지 최신 데이터만 유지하고 나머지는 버림
         */


        /**
         * observeOn : 데이터를 받는 측의 스레드 종류를 설정 :
         *  - 데이터를 받는 측의 스레드 종류를 설정한 수 있다.
         *  - 연산자(map, flatMap ...) 마다 서로 다른 스케줄러를 지정할 수 있기 때문에 여러 번 선언될 수 있다. 따라서 선언하는 순서가 중요하다.
         *  - Publisher의 데이터 생성 속도는 빠른데, subscriber의 데이터 처리 속도가 느린 경우에 주로 사용
         *
         * subscribeOn : 생성자의 측의 스레드 종류를 설정
         *  - 생산자의 처리 작업을 실행하는 스레드의 종류를 설정한다.
         *  - 여러번 호출되더라도 가장 처음의 subscribeOn 이 적용되며, 나머지는 무시된다.
         *  - 첫번째 스트림 ~ observeOn 호출 전까지의 스트림의 스레드를 지정한다.
         *  - publisher의 데이터 생성 속도가 느리고(e.g. blocking I.O), subscriber의 처리 속도는 빠른 경우에 주로 사용
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
                    System.out.println("onNext : 2초 대기");
                    sleepNoEx(2_000L);

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

        sleepNoEx(10_000L);


    }

}
