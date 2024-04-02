package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.function.Consumer;

@Slf4j
public class Ex8_2BackpressureMode_Refactoring {

    private static Consumer<Long> printInt = data -> {
        log.info("# onNext: {}", data);

        try {
            Thread.sleep(5L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    private static Consumer<Throwable> errorProcess = error -> log.error("# onError");


    /**
     * 팩토리 메서드 - Flux를 생성
     * @return Flux<Long>
     */
    private static Flux<Long> getFluxInterval() {
        return Flux.interval(Duration.ofMillis(1L));
    }

    /** 메서드 분리(재활용) + 옵저버패턴 + 전략패턴
     * .subscribe(printInt, errorProcess)
     *
     * @param flux
     * @return
     */
    private static Disposable publishAndSubscribe(Flux<Long> flux) {
        return flux.publishOn(Schedulers.parallel())
                .subscribe(printInt, errorProcess);     // Observer + 전략패턴
    }

    @Test
    void BackpressureTest_Error() throws InterruptedException {

        Flux<Long> flux = getFluxInterval()
                .onBackpressureError()
                .doOnNext(d -> log.info("# DoOnNext: {}", d))
                ;

        publishAndSubscribe(flux);

        Thread.sleep(2000L);
    }

    @Test
    void BackpressureTest_Error2() throws InterruptedException {

        publishAndSubscribe(getFluxInterval()
                .onBackpressureError()
                .doOnNext(d -> log.info("# DoOnNext: {}", d))
        );

        Thread.sleep(2000L);
    }

    @Test
    void BackpressureTest_Drop() throws InterruptedException {

        publishAndSubscribe(
                getFluxInterval()
                .onBackpressureDrop(d -> log.info("# dropped: {}", d))
        );

        Thread.sleep(2000L);
    }

}
