package com.example.reactor;

import com.jayway.jsonpath.JsonPath;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Ex1_1HelloReactor {

    @Test
    void helloReactorFlux() {
        Flux<String> sequence = Flux.just("Hello", "Reactor");

        System.out.println("== Flux Subscribe ==");
        sequence.map(String::toUpperCase)
                .subscribe(System.out::println);


        System.out.println("""
                
                == toStream.Collect(Join(" ") ==""");
        String result = sequence.map(String::toUpperCase)
                .toStream().collect(Collectors.joining(" "));
        System.out.println(result);
    }

    @Test
    void helloReactorMono() {
        Mono<String> sequence = Mono.just("Hello Reactor");

        System.out.println("== Mono Subscribe ==");
        sequence.map(String::toUpperCase)
                .subscribe(System.out::println);

        CompletableFuture<String> cfString = sequence.map(String::toUpperCase)
                .toFuture();
        try {
            String result2 = cfString.get();
            System.out.println(result2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("""
                \n== toStream.Collect(Join(" ") ==""");
        String result = sequence.map(String::toUpperCase)
                .block();
        System.out.println(result);

    }

    @Test
    void MonoSignal() {
        Mono.just("Test")
                .subscribe(
                        outPrintln
                        , printError
                        , printSignal
                );
    }

    @Test
    void printCurrentAsiaDateTime() {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate.exchange(worldTimeUri,
                        HttpMethod.GET,
                        new HttpEntity<String>(headers),
                        String.class)
        ).map(response -> JsonPath.parse(response.getBody()))
                .subscribe(
                        data -> System.out.println(data.read("$.datetime").toString())
                        , printError
                        , printSignal
                );

        System.out.println("=== Test ===");

    }

    @NotNull
    private static Runnable printSignal = () -> System.out.println("Signal : onComplete");

    @NotNull
    private static Consumer<Throwable> printError = error -> System.out.println("Error : " + error);

    @NotNull
    public static Consumer<String> outPrintln = data -> System.out.println("Print : " + data);

}
