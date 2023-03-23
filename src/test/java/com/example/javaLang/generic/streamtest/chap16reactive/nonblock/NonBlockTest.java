package com.example.javaLang.generic.streamtest.chap16reactive.nonblock;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NonBlockTest {
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
        IntStream.rangeClosed(0, 0).forEach(i -> {
            Shop.delay(1);
            System.out.println("Do something else - " + i);
        });
    }

    /**
     * 블록킹이 되는 일반적인 방식으로 조회 처리
     * @param product
     * @return
     */
    public List<String> findPricesByBlockingCall(String product) {
        List<String> result = shops.stream().map(shop ->
                        getPriceByShop(shop, product)
                ).collect(Collectors.toList());

        System.out.println("블로킹 방식 스트림 처리 이후 코드");
        doSomethingElse();

        return result;
    }

    private String getPriceByShop(Shop shop, String product) {
        System.out.println(shop.shopName() + " 가격 조회 중...");
        return String.format("%s price is %.2f \n", shop.shopName(), shop.getPrice(product));
    }

    @Test
    void BlockingTest() {
        System.out.println("\n==Blocking Test==");
        System.out.println(findPricesByBlockingCall("myPhone14"));
    }

    /**
     * 패러럴 스트림을 이용해서 병렬로 데이터 처리
     * @param product
     * @return
     */
    public List<String> findPricesByNonBlocking(String product) {
        List<String> result = shops.parallelStream().map(shop -> getPriceByShop(shop, product)
        ).collect(Collectors.toList());

        System.out.println("논블로킹 방식 스트림 처리 이후 코드");
        doSomethingElse();

        return result;
    }

    @Test
    void NonBlockingTest() {
        System.out.println("\n==Non-Blocking Test==");
        System.out.println(findPricesByNonBlocking("myPhone14"));
    }

    public List<String> findPriceByCompletableFuture(String product) {
        List<CompletableFuture<String>> prices =
                shops.stream().map(
                        shop -> CompletableFuture.supplyAsync(      //내부의 호출을 비동기로 처리한다.
                                () -> getPriceByShop(shop, product)
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


    public List<String> findPriceByCompletableFutureConnectJoin(String product) {
        List<String> result =
                shops.stream().map(
                        shop -> CompletableFuture.supplyAsync(      //내부의 호출을 비동기로 처리한다.
                                () -> getPriceByShop(shop, product)
                        )
                ).map(CompletableFuture::join) //join으로 인해 이후 코드를 병렬 처리하지 못하고, join의 결과를 기다리게 된다.
                .collect(Collectors.toList())
                ;

        System.out.println("findPriceByCompletableFutureConnectJoin 방식 스트림 처리 이후 코드");
        doSomethingElse();

        return result;
    }

    @Test
    @Disabled
    void FutureCompletableJoinTest() {
        System.out.println("\n==FutureCompletableJoin Test==");
        System.out.println(findPriceByCompletableFutureConnectJoin("myPhone14"));
    }
}
