package com.example.javaLang.generic.streamtest;

import com.example.javaLang.generic.streamtest.entity.Trader;
import com.example.javaLang.generic.streamtest.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class Stream177Test {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    Supplier<Stream<Transaction>> streamSupplier = () -> transactions.stream();

    // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
    @Test
    void quiz1() {
        var trs = streamSupplier.get();

        trs.filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .forEach( t -> System.out.println(t.getValue()));
    }

    // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
    @Test
    void quiz2() {
        var trs = streamSupplier.get();

        var cities = trs.map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        cities.stream().forEach(System.out::println);
    }

    // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시요.
    @Test
    void quiz3() {
        var trs = streamSupplier.get();

        var tradersInCambridge = trs.filter(t -> t.getTrader().getCity() == "Cambridge")
                .map(t->t.getTrader().getName())
                .distinct()
                .sorted(comparing(String::toString))
                .collect(Collectors.toList());

        tradersInCambridge.stream().forEach(System.out::println);
    }

    // 4. 모든 거래자의 이름을 알파벳으로 정렬해서 반환하시오.
    @Test
    void quiz4() {
        var trs = streamSupplier.get();

        var results = trs.map(t->t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(results);

    }

    // 5. 밀라노에 거래자가 있는가?
    @Test
    void quiz5() {
        var trs = streamSupplier.get();

        var result = trs.filter(t->t.getTrader().getCity() == "Milan")
                .findAny().orElse(null);

        System.out.println(result);

        trs = streamSupplier.get();
         var result2 = trs.anyMatch(t->t.getTrader().getCity().equals("Milan"));

        System.out.println(result2);
    }

    // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
    @Test
    void quiz6() {
        var trs = streamSupplier.get();

        trs.filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
    @Test
    void quiz7() {
        var trs = streamSupplier.get();
        var result = trs.map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println(result);

        var result2 = streamSupplier.get()
                .mapToInt(Transaction::getValue)
                .max().orElse(0);

        System.out.println(result2);
    }

    // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
    @Test
    void quiz8() {
        var trs = streamSupplier.get();
        var result = trs.map(Transaction::getValue)
                .reduce(Integer::min);

        var result2 = streamSupplier.get()
                .mapToInt(Transaction::getValue)
                .min().orElse(0);


        System.out.println(result);

    }



}
