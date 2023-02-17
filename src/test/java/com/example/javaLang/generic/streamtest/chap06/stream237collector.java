package com.example.javaLang.generic.streamtest.chap06;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class stream237collector {

    // 커스텀 콜렉터 방식
    public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumbersCollector());
    }

    // 단순 스트림 방식
    private Map<Boolean, List<Integer>> partitionPrimes(int candidate) {
        var result = IntStream.rangeClosed(2, candidate)
                .boxed()
                .collect(partitioningBy(
                        i -> isPrime(i)
                ));

        return result;
    }

    /**
     * 소수 여부
     * @param candidate
     * @return
     */
    private boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);

        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    @Test
    void 단순스트림방식_소수구하기_성능() {
        long fastest = Long.MAX_VALUE;

        Map<Boolean, List<Integer>> result = null;

        for(int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            result = partitionPrimes(1_000_000); // 백만 개의 숫자를 소수와 비소수로 분할한다.
            long duration = (System.nanoTime() - start) / 1_000_000;

            if(duration < fastest) fastest = duration;
        }

        System.out.println("일반 스트림 방식 : 가장 빠른 실행은 " + fastest + "msecs");

        var result1 = result.get(true).stream().limit(20).map(String::valueOf).collect(Collectors.joining(", "));
        System.out.println(result1);

    }

    @Test
    void 커스텀방식_소수구하기_성능() {
        long fastest = Long.MAX_VALUE;

        Map<Boolean, List<Integer>> result = null;

        for(int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            result = partitionPrimesWithCustomCollector(1_000_000); // 백만 개의 숫자를 소수와 비소수로 분할한다.
            long duration = (System.nanoTime() - start) / 1_000_000;

            if(duration < fastest) fastest = duration;
        }

        System.out.println("커스텀 콜렉터 방식 : 가장 빠른 실행은 " + fastest + "msecs");

        var result1 = result.get(true).stream().limit(20).map(String::valueOf).collect(Collectors.joining(", "));
        System.out.println(result1);

    }
}
