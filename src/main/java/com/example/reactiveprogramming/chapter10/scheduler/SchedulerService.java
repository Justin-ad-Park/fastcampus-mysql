package com.example.reactiveprogramming.chapter10.scheduler;

import com.example.aop.stopwatch.StopWatchOn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class SchedulerService {


    @StopWatchOn(methodName = "subscribeOn")
    public void subscribeOn() {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())   //subscribeOn() 오퍼레이터는 구독이 발생한 직후에 원본 Publisher의 동작을 처리하기 위해 스레디를 할당한다.
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    @StopWatchOn(methodName = "Parallel without runOn")
    public void parallel() {
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel(4)
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    @StopWatchOn(methodName = "Parallel with runOn")
    public void parallelWithRunOn() {
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel(4)
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));
    }

}
