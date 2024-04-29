package com.example.reactiveprogramming.chapter10.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


/**
 * <pre>
 * Flux 스트림에서 subscribeOn과 publishOn 메소드, 그리고 parallel 연산자는
 * 리액티브 프로그래밍에서 스트림의 실행 컨텍스트(스레드)를 관리하는 데 사용됩니다.
 * 이들의 주된 차이점은 실행되는 스레드를 어떻게 변경하는지에 있습니다.
 *
 * <h2><font color='00FF00'>Scheduler Operator 종류</font></h2>
 * <h3><font color='A0A0A0'>subscribeOn</font></h3>
 * subscribeOn은 구독이 발생할 때 사용될 스케줄러를 지정합니다.<br>
 * 즉, 소스 스트림이 실행될 스레드를 정합니다.<br>
 * 이는 스트림의 처리 파이프라인 전체에 영향을 미치며, 데이터의 생성과 처리가 지정된 스케줄러에서 수행됩니다.<br>
 * subscribeOn은 여러 번 호출되어도 첫 번째 호출에 의해 결정된 스케줄러를 사용합니다.<br>
 * subscribeOn은 Operator 체인상의 어떤 위치에 있든 간에 구독 시점 직후, <br>
 * 즉, Publisher가 데이터를 emit하기 전에 실행 스레드를 변경합니다.
 *
 *<p/>
 * <h3><font color='A0A0A0'>publishOn</font></h3>
 * publishOn은 스트림 중간에 다음 연산이 실행될 스케줄러를 변경합니다.<br>
 * 이는 호출된 지점 이후의 연산에만 영향을 미치며, <br>
 * 여러 번 호출될 경우 각 호출 지점 이후의 연산이 해당 스케줄러에서 실행됩니다.<br>
 * 이를 통해 스트림 처리의 특정 부분을 다른 스레드에서 실행할 수 있습니다.
 *
 *<p/>
 * <h3><font color='A0A0A0'>Parallel</font></h3>
 * parallel은 스트림을 병렬 처리하기 위해 사용됩니다.<br>
 * 이 연산자는 스트림의 데이터를 여러 "레일"로 분할하여, 각 레일을 병렬로 처리할 수 있게 합니다.
 *
 * </pre>
 */

@Slf4j
@SpringBootTest()
public class SchedulerTest {

/**
 * <pre>
 * <h2><font color='00FF00'>Schedulers 종류</font></h2>
 * <h3><font color='A0A0A0'>Schedulers.immediate()</font></h3>
 * 별도 스레드를 추가적으로 생성하지 않고, 현재 스레드에서 작업을 처리한다.
 *
 * <p/>
 * <h3><font color='A0A0A0'>xx</font>Scheduers.single()</h3>
 * 스레드 하나만 생성해서 Scheduler가 제거되기 전까지 재사용하는 방식
 *
 * <p/><h3><font color='A0A0A0'>Schedulers.newSingle()</font></h3>
 * 호출할 때마다 매번 새로운 스레드 하나를 생성
 *
 *<p/><h3><font color='A0A0A0'>Schedulers.boundedElastic()</font></h3>
 * ExecutorService 기반의 스레드 풀을 생성한 후, 그 안에서 정해진 수만큼의 스레들르 사용하여 작업을 처리한다.<br>
 * 작업이 종료된 스레드는 반납하여 재사용하는 방식<br>
 * Blocking I/O(File, DB, Network) 작업을 효과적으로 처리하기 위한 방식<br>
 * Blocking I/I 작업이 포함된 경우, 다른 Non-Blocking 처리에 영향을 주지 않도록
 * 전용 스레드를 할당해서 Blocking I/O 작업을 처리하기 때문에 효율적이다.
 *
 * <p/><h3><font color='A0A0A0'>Schedulers.parallel()</font></h3>
 * Non-Blocking I/O에 최적화. CPU 코어 수만큼 스레드를 생성한다.
 *
 * <p/><h3><font color='A0A0A0'>Schedulers.fromExecutorService()</font></h3>
 * 기존에 이미 사용하고 있는 ExecutorService가 있다면 그로부터 Scheduler를 생성하는 방식
 * ExecutorService로부터 직접 생성할 수도 있지만, Reactor에서는 이 방식을 권장하지 않는다.
 *
 * <p/><h3><font color='A0A0A0'>Schedulers.newXXX()</font></h3>
 * 매번 새로운 Scheduler 인스턴스를 생성한다.
 *
 * </pre>
 */

    @Autowired
    SchedulerService schedulerService;

    //subscribeOn
    @Test
    void Scheduler_test() throws InterruptedException {

        schedulerService.subscribeOn();

//        Flux.fromArray(new Integer[] {1, 3, 5, 7})
//                .subscribeOn(Schedulers.boundedElastic())   //subscribeOn() 오퍼레이터는 구독이 발생한 직후에 원본 Publisher의 동작을 처리하기 위해 스레디를 할당한다.
//                .doOnNext(data -> log.info("# doOnNext: {}", data))
//                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
//                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    //publishOn
    @Test
    void Scheduler_publishOn_test() throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .publishOn(Schedulers.parallel())   // Downstream으로 데이터를 emit하는 스레드를 변경한다.
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    //both : subscribeOn + publishOn
    @Test
    void Scheduler_both_test() throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .publishOn(Schedulers.parallel())   // Downstream의 실행 스레드를 변경한다.
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    //Parallel
    /**
     * parallel() 기본 사용 예제
     * - parallel()만 사용할 경우에는 병렬로 작업을 수행하지 않는다.
     * - runOn()을 사용해서 Scheduler를 할당해주어야 병렬로 작업을 수행한다.
     * - **** CPU 코어 갯수내에서 worker thread를 할당한다. ****
     */

    @Test
    void ParallelWithoutRunOn_test() throws InterruptedException {
        schedulerService.parallel();

//        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
//                .parallel(4)
//                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(100L);
    }

    @Test
    void ParallelWithRunOn_test() throws InterruptedException {
        schedulerService.parallelWithRunOn();

//        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
//                .parallel(4)
//                .runOn(Schedulers.parallel())
//                .subscribe(data -> log.info("# onNext: {}", data));


        Thread.sleep(100L);
    }
}
