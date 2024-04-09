package com.example.lambdapattern._20removeduplicatestream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class BackpressureMode_RefactoringFinal2 {
    /**
     * <pre>
     * 대부분의 람다 함수를 사용해서 메서드를 제공하는 방식은 전략 패턴(Strategy pattern)을 활용하는 것이다.
     *
     * = 전략패턴 =
     * 정의 : 전략 인터페이스와 이 인터페이스를 구현하는 클래스로 구성되며 비교적 간단하다.
     *
     * 복잡한 if-else, switch-case 분기를 피하기 위해 사용됨
     * 알고리즘(로직)을 개별적으로 캡슐화하여, 이를 서로 교환 가능하게 만드는 것
     * 전략 패턴은 독립적으로 알고리즘을 변경할 수 있다.
     *
     * 로직의 많은 부분이 중복되고, 일부 알고리즘(로직)만 다른 경우에도 사용할 수 있다.
     *
     * = 템플릿 메서드 패턴과 차이 =
     * 템플릿 메서드 패턴은 변경이 필요한 알고리즘 영역을 추상 클래스의 추상 메서드로 선언하고,
     * 상속받은 클래스에 추상 메서드를 Override 함으로서 알고리즘의 변경을 가능하게 하는데 반해
     * 전략패턴은 인터페이스의 구현을 통해 알고리즘을 제공하는 점이 다르다.
     *
     * 이 차이로 인해 람다함수에서는 전략패턴을 활용하는 것이 훨씬 구현이 쉽다.
     * </pre>
     */
    /**
     * <pre>
     * Execute Around 패턴 + 빌더 패턴
     *  빌더 패턴 : 자기 자신을 리턴
     *  Execute Around 패턴 : 공통 로직은 Execute Around 코드 내에서 처리를 해주고,
     *  사용자는 함수형 인터페이스를 통해 세부 로직의 구현에만 신경을 씀
     *  </pre>
     * @param fluxModifier
     * @throws InterruptedException
     */
    private void subscribeWithBackpressure(Function<Flux<Long>, Flux<Long>> fluxModifier) throws InterruptedException {

        Flux.interval(Duration.ofMillis(1L))
                .transform(fluxModifier)
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
                f -> f.onBackpressureDrop(d -> log.info("# dropped: {}", d))
        );
    }

    @Test
    void BackpressureTest_Latest() throws InterruptedException {
        subscribeWithBackpressure(
                f -> f.onBackpressureLatest()
        );
    }


    private static Consumer<Long> printInt = data -> {
        log.info("# onNext: {}", data);

        try {
            Thread.sleep(5L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    private static Consumer<Throwable> errorProcess = error -> log.error("# onError");

}
