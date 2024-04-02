package com.example.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Ex8_1Backpressure {

    @Test
    void Ex8_1Test() {
        Flux.range(101, 10)
                .doOnRequest(d -> log.info("# doOnRequest: {}", d))
                .subscribe(new BaseSubscriber<Integer>() {
                    int count = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @SneakyThrows
                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("# HoodOnNext : {}", value);

                        count++;
                        if(count % 2 == 1) {
                            Thread.sleep(2000L);
                            request(2);
                        }
                    }
                });
    }

    @Test
    void Ex8_1_2_Test() {
        Flux.range(101, 10)
                .doOnRequest(d -> log.info("# doOnRequest: {}", d))
                .subscribe(new IntegerSubscriber());


    }

    protected class IntegerSubscriber extends BaseSubscriber<Integer> {
        int count = 0;

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            request(1);
        }

        @SneakyThrows
        @Override
        protected void hookOnNext(Integer value) {
            log.info("# HoodOnNext : {}", value);

            count++;
            if(count % 2 == 1) {
                Thread.sleep(2000L);
                request(2);
            }
        }
    }
}
