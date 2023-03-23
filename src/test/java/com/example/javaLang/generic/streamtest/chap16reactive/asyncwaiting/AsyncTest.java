package com.example.javaLang.generic.streamtest.chap16reactive.asyncwaiting;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AsyncTest {
    public void doSomethingElse() {
        IntStream.rangeClosed(0, 1).forEach(i -> {
            Shop.delay(1);
            System.out.println(i);
        });
    }

    //쓰레드가 완료되면 Futher를 리턴하는 이 방식은
    // 쓰레드가 종료되지 않으면 클라이언트는 무한 대기에 빠지며,
    // 쓰레드 내부에서 에러가 발생해도 클라이언트는 어떤 에러가 발생했는지 알 수 없다.
    public Future<Double> getPriceAsync(Shop shop, String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        //다른 스레드에서 비동기로 실행한다.
        new Thread( () -> {
            double price = shop.getPrice(product);
            futurePrice.complete(price);    //스레드에서 계산이 계산이 완료되면 Future에 값을 설정한다.

        }).start();

        return futurePrice;
    }

    @Test
    void ASyncTest() {
        Shop shop = new Shop("shopName");
        long start = System.nanoTime();

        Future<Double> futurePrice = getPriceAsync(shop, "my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        doSomethingElse();

        try{
            double price = futurePrice.get();   //가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
            System.out.printf("My price is %.2f%n", price);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after" + retrievalTime + " msec");

    }

    /**
     * CompleteFuture를 이용해 Timeout 설정 및 Exception 전달을 할 수 있다.
     * @return
     */
    public Future<Double> getPriceAsyncWithCompleteFuture(Shop shop, String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = shop.getPrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);  //에러가 발생하면 발생한 에러를 포함시켜 Future를 종료한다.
            }
        }).start();

        return futurePrice;
    }

    @Test
    void ASyncWithCompleteFutureTest() {
        Shop shop = new Shop("shopName");

        Future<Double> futurePrice = getPriceAsyncWithCompleteFuture(shop, "His favorite product");

        doSomethingElse();

        try{
            double price = futurePrice.get();   //가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
            System.out.printf("His price is %.2f%n", price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 이 방식은 위의 getPriceAsyncWithCompleteFuture와 동일하게 동작한다.
     *  궁금하면, CompletableFuture.AsyncSupply<T>의 run() 메소드의 소스코드를 확인해 보면 됩니다.
     *
     * @param shop
     * @param product
     * @return
     */
    public Future<Double> getPriceWithCompleteFutureEasyVersion(Shop shop, String product) {
        return CompletableFuture.supplyAsync(() -> shop.getPrice(product));
    }

    @Test
    void ASyncEasyVersionTest() {
        Shop shop = new Shop("shopName");

        Future<Double> futurePrice = getPriceWithCompleteFutureEasyVersion(shop, "your favorite product");

        doSomethingElse();

        try{
            double price = futurePrice.get();   //가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
            System.out.printf("Your price is %.2f%n", price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
