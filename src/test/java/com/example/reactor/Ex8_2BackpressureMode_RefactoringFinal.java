package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class Ex8_2BackpressureMode_RefactoringFinal {

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
     * 빌더 패턴 + 람다 함수를 활용한 전략 패턴
     *  빌더 패턴 : 자기 자신을 리턴
     *  전략 패턴 : 함수형 인터페이스를 통해 세부 로직을 사용자 측에서 정의할 수 있도록 함
     * @param fluxModifier
     * @throws InterruptedException
     */
    private void subscribeWithBackpressure(Function<Flux<Long>, Flux<Long>> fluxModifier) throws InterruptedException {

        Flux<Long> flux = Flux.interval(Duration.ofMillis(1L)); //flux 생성

        fluxModifier.apply(flux)    //fluxModifier(Funciton<T,R>)의 파라미터로 주입
                .publishOn(Schedulers.parallel())
                .subscribe(printInt, errorProcess);

        Thread.sleep(2000L);
    }

    @Test
    void BackpressureTest_Error() throws InterruptedException {
        subscribeWithBackpressure(
                f -> f.onBackpressureError()
                        .doOnNext(d -> log.info("# DoOnNext: {}", d)));
    }

    @Test
    void BackpressureTest_Drop() throws InterruptedException {
        subscribeWithBackpressure(
                f -> f.onBackpressureDrop(d -> log.info("# dropped: {}", d)));
    }
}
