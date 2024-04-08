package com.example.reactiveprogramming.chapter08.backpressurebuffer;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackpressureModeByLambda {

    @Test
    void BackpressureTest_bufferLATEST() throws InterruptedException {
        execute(300L, BufferOverflowStrategy.DROP_LATEST, false, 1);

        Thread.sleep(2500L);
    }


    @Test
    void BackpressureTest_bufferOLDEST() throws InterruptedException {
        execute2(300L, BufferOverflowStrategy.DROP_OLDEST, false, 1);

        Thread.sleep(2500L);
    }


    private static Flux<Long> createFluxStream(long intervalMillis) {
        return Flux.interval(Duration.ofMillis(intervalMillis))
                .doOnNext(d -> log.info("# emitted by original Flux: {}", d));
    }


    private static void execute(long intervalMillis, BufferOverflowStrategy bufferOverFlowStrategy, boolean delayError, int prefetch) {
        createFluxStream(intervalMillis)
                .onBackpressureBuffer(2,
                        d -> log.info("xxx Overflow & Dropped: {} ", d),
                        bufferOverFlowStrategy)
                .doOnNext(d -> log.info("[emitted by Buffer: {} ]", d))
                .publishOn(Schedulers.parallel(), delayError, prefetch)
                .subscribe(getStandardSubscriber);
    }


    private static void execute2(long intervalMillis, BufferOverflowStrategy bufferOverFlowStrategy, boolean delayError, int prefetch) {
        createFluxStream(intervalMillis)
                .onBackpressureBuffer(2,
                        d -> log.info("xxx Overflow & Dropped: {} ", d),
                        bufferOverFlowStrategy)
                .transform(flux -> publishOn(flux, delayError, prefetch))
                .subscribe(getStandardSubscriber);
    }

    private static Flux<Long> publishOn(Flux<Long> flux, boolean delayError, int prefetch) {
        return flux.doOnNext(d -> log.info("[emitted by Buffer: {} ]", d))
                .publishOn(Schedulers.parallel(), delayError, prefetch)
                ;
    }

    @NotNull
    private static Subscriber<Long> getStandardSubscriber = new Subscriber<>() {
        @Override
        public void onSubscribe(Subscription subscription) {
        }

        @Override
        public void onNext(Long d) {
            log.info("---> onNext: {}", d);

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
        }

        @Override
        public void onError(Throwable error) {
            log.error("# onError", error);
        }

        @Override
        public void onComplete() {
            log.error("# onComplete");
        }
    };
}
