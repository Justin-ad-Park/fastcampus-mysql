package com.example.javaLang.generic.lambdapattern._20removeduplicatestream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackpressureMode_Origin {

    @Test
    void BackpressureTest_Error() throws InterruptedException {

        Flux.interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(d -> log.info("# DoOnNext: {}", d))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            log.info("# onNext: {}", data);

                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        error -> log.error("# onError")
                );

        Thread.sleep(2000L);
    }


    @Test
    void BackpressureTest_Drop() throws InterruptedException {

        Flux.interval(Duration.ofMillis(1L))
                .onBackpressureDrop(d -> log.info("# dropped: {}", d))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            log.info("# onNext: {}", data);

                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        error -> log.error("# onError")
                );


        Thread.sleep(2000L);
    }

}
