package com.example.reactiveprogramming.chapter09.sinks;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

@Slf4j
public class SinksTest {

    /**
     * 하나의 emit만 처리하는 one()
     */
    @Test
    void sinks_One_test() {
        Sinks.One<String> sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", FAIL_FAST);
        sinkOne.emitValue("Hi Reactor", FAIL_FAST); //Dropped - Sinks.one은 처음 emit한 데이터만 emit되며, 나머지는 Drop됨
        sinkOne.emitValue(null, FAIL_FAST);

        mono.subscribe(data -> log.info("# Subscriber1 {}", data));
        mono.subscribe(data -> log.info("# Subscriber2 {}", data));
    }

    /**
     * 하나의 subscriber만 허용하는 unicast()
     */
    @Test
    void sinks_unicast_test() {
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Integer> fluxView = unicastSink.asFlux();

        unicastSink.emitNext(1, FAIL_FAST);
        unicastSink.emitNext(2, FAIL_FAST);


        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));

        unicastSink.emitNext(3, FAIL_FAST);

        /**
         *  unicast()는 하나의 subscriber만 허용(allows only a single Subscriber)하며,
         *  2 이상이 되면 에러(onErrorDropped)가 발생한다.
         */
        // fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));
    }

    /**
     * 여러 subscriber를 허용하는 multicast()
     */
    @Test
    void sinks_multicast_test() {
        Sinks.Many<Integer> multicastSink =
                Sinks.many().multicast().onBackpressureBuffer();
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, FAIL_FAST);
        multicastSink.emitNext(2, FAIL_FAST);

        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));
        fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));

        multicastSink.emitNext(3, FAIL_FAST);
    }

    /**
     * 지정한 숫자만큼 버퍼를 이용하는 replay().limit(n)
     * replay는 MulticastReplaySpec을 리턴하며, limit() 메서드를 통해 replay하는 emit의 갯수를 지정한다.
     */
    @Test
    void sinks_replay_test() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().limit(2);
        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));

        replaySink.emitNext(4, FAIL_FAST);

        fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));
    }
}
