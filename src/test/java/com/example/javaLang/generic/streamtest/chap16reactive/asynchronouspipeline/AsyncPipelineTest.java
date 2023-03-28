package com.example.javaLang.generic.streamtest.chap16reactive.asynchronouspipeline;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AsyncPipelineTest {
    List<Shop> shops = Arrays.asList(new Shop("B-Mart"),
            new Shop("C-mart"),
            new Shop("S-mart"),
            new Shop("4-Mart"),
            new Shop("5-Mart"),
            new Shop("6-Mart"),
            new Shop("7-Mart"),
            new Shop("8-Mart"),
            new Shop("9-Mart")
    );


    private final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    r -> {
                        Thread t = new Thread(r);
                        t.setDaemon(true);  // 데몬 스레드로 만들면, 프로그램 종료 시 스레드가 함께 종료된다.
                        return t;
                    }
            );


    public List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> shop.getDiscountPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    void test() {
        System.out.println(findPrices("IP14"));
    }

    public List<String> findPricesPipeline(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                    .map(shop -> CompletableFuture.supplyAsync(
                            () -> shop.getDiscountPrice(product), executor))    //시간이 걸리는 작업을 비동기로
                    .map(future -> future.thenApply(Quote::parse))
                    .map(future -> future.thenCompose( quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote) + "\n", executor)))  //시간이 걸리는 작업을 비동기로
                    .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    @Test
    void pipelineTest() {
        System.out.println(findPricesPipeline("Test14"));
    }


}
