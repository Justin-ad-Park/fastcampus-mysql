package com.example.reactiveprogramming.chapter08.backpressurebuffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackPressureModeOrigin {

    @Test
    void BackpressureTest_buffer_LATEST() throws InterruptedException {
        Flux.interval(Duration.ofMillis(300L))
                .doOnNext(d -> log.info("# emitted by original Flux: {}", d))
                .onBackpressureBuffer(2,
                        d -> log.info("xxx Overflow & Dropped: {} ", d),
                        BufferOverflowStrategy.DROP_LATEST)
                .doOnNext(d -> log.info("[emitted by Buffer: {} ]", d))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(d -> {
                            log.info("---> onNext: {}", d);

                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {}
                        },
                        error -> log.error("# onError", error));

        Thread.sleep(5000L);
    };

    @Test
    void BackpressureTest_buffer_OLDEST() throws InterruptedException {
        Flux.interval(Duration.ofMillis(300L))
                .doOnNext(d -> log.info("# emitted by original Flux: {}", d))
                .onBackpressureBuffer(2,
                        d -> log.info("xxx Overflow & Dropped: {} ", d),
                        BufferOverflowStrategy.DROP_OLDEST)
                .doOnNext(d -> log.info("[emitted by Buffer: {} ]", d))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(d -> {
                            log.info("---> onNext: {}", d);

                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {}
                        },
                        error -> log.error("# onError", error));

        Thread.sleep(5000L);
    };

}
