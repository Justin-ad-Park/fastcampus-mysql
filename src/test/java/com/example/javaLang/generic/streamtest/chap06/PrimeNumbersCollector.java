package com.example.javaLang.generic.streamtest.chap06;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PrimeNumbersCollector implements Collector<Integer,
        Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>(){{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int)Math.sqrt(candidate);
        return primes.stream()
            .takeWhile(i -> i <= candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(isPrime(acc.get(true),
                    candidate)
            ).add(candidate);
        };

    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner()
    {
        return (Map<Boolean, List<Integer>> map1,
        Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));

            return map1;
        };

        //throw new UnsupportedOperationException("소수 구하기에서는 combiner 메소드가 호출되지 않습니다.");
    }

    /**
     * 데이터 변환이 필요하지 않으므로 항등 함수(identity())를 반환한다.
     * @return
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        //순서대로 처리해야 하기 때문에 IDENTITY_FINISH는 맞지만, UNORDERED, CONCURRENT는 아니다.
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
