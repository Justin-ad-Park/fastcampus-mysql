package com.example.javaLang.generic.streamtest.chap16reactive.nonblockwithexecutor;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NonBlockWithExecutorTest {
    StopWatch stopWatch = new StopWatch();

    List<Shop> shops = Arrays.asList(new Shop("B-Mart"),
            new Shop("C-mart"),
            new Shop("S-mart"),
            new Shop("4-Mart"),
            new Shop("5-Mart"),
            new Shop("6-Mart"),
            new Shop("7-Mart"),
            new Shop("8-Mart"),
            new Shop("9-Mart"),
            new Shop("10-Mart"),
            new Shop("11-Mart"),
            new Shop("12-Mart"),
            new Shop("13-Mart"),
            new Shop("14-Mart"),
            new Shop("15-Mart"),
            new Shop("16-Mart"),
            new Shop("17-Mart"),
            new Shop("18-Mart"),
            new Shop("19-Mart"),
            new Shop("20-Mart"),
            new Shop("21-Mart"),
            new Shop("22-Mart"),
            new Shop("23-Mart"),
            new Shop("24-Mart"),
            new Shop("25-Mart"),
            new Shop("26-Mart"),
            new Shop("27-Mart"),
            new Shop("28-Mart")
    );

    private final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    r -> {
                        Thread t = new Thread(r);
                        t.setDaemon(true);  // 데몬 스레드로 만들면, 프로그램 종료 시 스레드가 함께 종료된다.
                        return t;
                    }
            );

    @BeforeEach
    void StartWatch() {
        stopWatch.start();
    }

    @AfterEach
    void stopWatch() {
        stopWatch.stop();
        System.out.println("Done in duration msec : " + stopWatch.getTotalTimeMillis());
    }

    public void doSomethingElse() {
        IntStream.rangeClosed(0, 10).forEach(i -> {
            Shop.delay(1);
            System.out.println("Do something else - " + i);
        });
    }

    private String getPriceByShop(Shop shop, String product) {
        System.out.println(shop.shopName() + " 가격 조회 중...");
        return String.format("%s price is %.2f \n", shop.shopName(), shop.getPrice(product));
    }

    /**
     * CompletableFuture를 활용해서 내부의 연산을 비동기(쓰레드) 연산으로 만듬
     * @param product
     * @return
     */
    public List<String> findPriceByCompletableFuture(String product) {
        List<CompletableFuture<String>> prices =
                shops.stream().map(
                        shop -> CompletableFuture.supplyAsync(      //내부의 호출을 비동기로 처리한다.
                                () -> getPriceByShop(shop, product), executor
                        )
                ).collect(Collectors.toList());

        System.out.println("CompletableFuture 방식 스트림 처리 이후 코드");
        doSomethingElse();

        List<String> result = prices.stream().map(CompletableFuture::join)     //모든 비동기 동작이 끝나길 기다린다.
                .collect(Collectors.toList());

        System.out.println("CompletableFuture 방식 join 이후 코드");

        return result;
    }

    @Test
    void FutureCompletableTest() {
        System.out.println("\n==FutureCompletable Test==");
        System.out.println(findPriceByCompletableFuture("myPhone14"));
    }

    /**
     * 사용 가능한 최대 CPU 확인하는 방법
     *
     * OSX(Macbook) :
     *  sysctl hw.physicalcpu hw.logicalcpu
     * LINUX :
     *  grep 'cpu cores' /proc/cpuinfo | tail -1
     *
     */
    @Test
    void getAvailableProcessors() {
        int nCPU = Runtime.getRuntime().availableProcessors();

        System.out.println(nCPU);
    }

    /**
     * 적정 스레드 풀을 찾는 방법
     *
     * Threads = nCPU * uCPU * (1 + W/C)
     *
     * nCPU = 프로세서 갯수 = Runtime.getRuntime().availableProcessors()
     * uCPU = CPU 사용율
     * W/C 비율 = Waiting time / Calculation time
     *
     * CPU 대기 시간이 서비스 시간보다 짧다면 CPU 개수보다 스레드가 적어야 성능이 좋다.
     * 반대로 대기시간이 서비스 처리 시간보다 많다면 스레드 수는 CPU 개수보다 많아야 한다.
     *
     *
     * 예)
     * Threads = 12CPU * 0.5(스레드에 할당할 CPU 사용량 40%) * (1 + 50ms 대기시간(=응답시간) / 5ms(처리 시간) )
     *      = 6 * (1 + 10) = 66개
     *
     *
     * 위의 예제를 예로 들면...
     * Threads = 12CPU * 0.5 * (1 + 1000 / 10)
     * 6 * 101 = 약 600
     *
     * 즉, 조회하는 샵의 갯수가 600개 까지는 스레드 갯수를 600으로 설정하면 1.2초 안으로 응답을 얻을 수 있다는 결론이 나온다.
     *
     *
     * 주의사항 : Local 개발 환경과 상용 서비스 환경은 다르다.
     * 과거 대용량 서버에 웹을 띄우던 시절에는 개발환경 보다 상용 환경이 훨씬 컴퓨팅 파워가 좋았지만,
     * K8s로 분산 처리하는 경우 각 Pod의 컴퓨팅 파워는 개발 환경보다 낮을 수 있다.
     */


    /**
     * I/O 작업, API 호출, DB 데이터 처리(SIUD) 등 대기시간이 발생하는 처리에서는 CompletableFuture로 최적화를 할 수 있다.
     *
     * 대기 시간이 거의 없는 CPU 연산 위주의 동작을 수행하는 경우에는 병렬 스트림이 구현하기 가장 간단하며
     * 효율적일 수 있다. (모든 스레드가 계산 작업을 수행하는 상황에서는 프로세스 코어 수 이상의 스레드를 가질 필요가 없다.)
     *
     * 또한, 실행환경의 CPU 파워를 예측하기 어려운 경우에도
     * 병렬스트림은 적절한 대안이 될 수 있다.
     *
     * 모든 처리를 쓰레드로 만들면, CPU 사용량을 예측하기 어렵고, 디버깅이나 프로세스 종료 문제 등에서
     * 문제 해결이 어려워질 수 있기 때문에 배치 또는 성능 개선의 효율이 있는 경우에 쓰레드를 사용하는 것이 좋다.
     */

}
