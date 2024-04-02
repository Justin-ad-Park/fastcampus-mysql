package com.example.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class Ex2_1HotSequence {

    @Test
    void Example2_1_Test() throws InterruptedException {
        String[] idolGroups = {"Black Pink", "(Girl)Idle", "LE SSERAFIM", "ITZY", "BTS"};

        Flux<String> concertLineup =
                Flux.fromArray(idolGroups)
                        .delayElements(Duration.ofSeconds(1))
                        .share();

        concertLineup.subscribe( s -> log.info("1st audience watching : " + s));

        Thread.sleep(2500);

        concertLineup.subscribe( s -> log.info("2PM audience watching : " + s));


        Thread.sleep(3000);
    }

}
