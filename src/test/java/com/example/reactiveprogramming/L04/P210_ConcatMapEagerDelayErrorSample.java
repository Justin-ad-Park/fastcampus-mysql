package com.example.reactiveprogramming.L04;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class P210_ConcatMapEagerDelayErrorSample {

    @Test
    void trueTest() {
        // 데이터 통지가 끝날 때까지 예외 통지를 미룬다.
        getConcatMapEagerDelayError(true);

        JSUtils.sleepNoEx(4000L);
    }

    @Test
    void falseTest() {
        // 즉시 예외 통지를 한다.
        getConcatMapEagerDelayError(false);

        JSUtils.sleepNoEx(4000L);
    }

    private void getConcatMapEagerDelayError(boolean tillTheEnd) {
        Flowable<String> flowable = Flowable.range(10, 3)   // 10, 11, 12
                .concatMapEagerDelayError(
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)    //0, 1, 2, 3 ...
                        .take(3)    // 3개 0, 1, 2
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
