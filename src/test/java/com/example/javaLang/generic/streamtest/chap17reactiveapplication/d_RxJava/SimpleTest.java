package com.example.javaLang.generic.streamtest.chap17reactiveapplication.d_RxJava;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import org.junit.jupiter.api.Test;
import io.reactivex.rxjava3.core.*;

import java.util.concurrent.TimeUnit;

public class SimpleTest {

    @Test
    void SimpleTest() {
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);

        onePerSec.subscribe(
                l -> System.out.println(TemperatureInfo.fetch("New York"))
        );

        /**
         * 아무 일도 일어나지 않는 것처럼 보인다.
         *  메인 쓰레드가 실행할 코드가 없어서 바로 종료되기 때문에 fetch가 되기도 전에 프로그램이 종료되었기 때문이다.
         */
    }


    @Test
    void SimpleTest_BlockingSubscribe() {
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);

        onePerSec.blockingSubscribe(
                l -> System.out.println(TemperatureInfo.fetch("New York"))
        );

        /**
         * 출력 결과가 표시된다.
         * 그렇지만 에러가 발생하는 상황에 대한 처리가 안되어 있기 때문에 예외가 발생하면 오류가 발생한다.
         */

    }
}
