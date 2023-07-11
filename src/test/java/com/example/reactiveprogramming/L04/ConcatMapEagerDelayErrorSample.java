package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ConcatMapEagerDelayErrorSample {

    @Test
    void trueTest() {
        getConcatMapEagerDelayError(true);  // 데이터 통지가 끝날 때까지 예외 통지를 미룬다.

        JSUtils.sleepNoEx(4000L);
    }

    @Test
    void falseTest() {
        getConcatMapEagerDelayError(false); // 즉시 예외 통지를 한다.

        JSUtils.sleepNoEx(4000L);
    }

    private void getConcatMapEagerDelayError(boolean tillTheEnd) {
        Flowable<String> flowable = Flowable.range(10, 3)
                .concatMapEagerDelayError(
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        .take(3)
                        .doOnNext(data -> {
                            if(sourceData == 11 && data ==1) {
                                throw new Exception("예외 발생");
                            }
                        })
                        .map(data -> "[" + sourceData + "]" + data),
                        tillTheEnd
                );

        flowable.subscribe(data -> System.out.println(data),
                error -> System.out.println(error)
                );
    }
}
