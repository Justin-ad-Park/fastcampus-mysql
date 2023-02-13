package com.example.javaLang.generic.streamtest.chap06;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class partition219 {

    /* 채식 여부로 메뉴를 구성 */
    @Test
    void 콜렉트_그룹핑_방식() {
        Map<Boolean, List<Dish>> partitionedByVegetarian =
                getMenuStream()
                .collect( groupingBy(Dish::isVegetarian)
                );

        System.out.println(partitionedByVegetarian);

        List<Dish> vegetarianDished = partitionedByVegetarian.get(true);
        System.out.println(vegetarianDished);
    }

    @Test
    void 콜렉트_파티셔닝_방식() {
        Map<Boolean, List<Dish>> partitionedByVegetarian =
                getMenuStream()
                        .collect(partitioningBy(Dish::isVegetarian)
                        );

        System.out.println(partitionedByVegetarian);

        List<Dish> vegetarianDishes = partitionedByVegetarian.get(true);
        System.out.println(vegetarianDishes);
    }

    @Test
    void 필터링_방식() {
        var vegetarianDishes=
                getMenuStream()
                        .filter(Dish::isVegetarian)
                        .collect(Collectors.toList());

        System.out.println(vegetarianDishes);
    }

    @Test
    void 파티셔닝_그룹핑_조합_방식() {
        var vegetarianDishes=
            getMenuStream()
                .collect(
                    partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)
                    )
                );

        System.out.println(vegetarianDishes);
    }

    @Test
    void 채식여부별_최대칼로리메뉴() {
        var result = getMenuStream()
            .collect(
                partitioningBy(Dish::isVegetarian,
                    collectingAndThen(
                        maxBy(Comparator.comparingInt(Dish::getCalories))
                        , Optional::get
                    )
                )
            );

        System.out.println(result);

        var result2 = getMenuStream()
            .collect(
                partitioningBy(Dish::isVegetarian,
                    maxBy(Comparator.comparingInt(Dish::getCalories))
                )
            );

        System.out.println(result2);
    }

    @Test
    void 퀴즈6_2_partitioningBy() {
        var result1 = getMenuStream()
            .collect(partitioningBy(Dish::isVegetarian,
                partitioningBy(
                    d -> d.getCalories() > 500
                )
            ));

        System.out.println(result1);

        var result2 = getMenuStream()
            .collect(partitioningBy(Dish::isVegetarian,
                groupingBy(Dish::getType)
            ));

        System.out.println(result2);

        var result3 = getMenuStream()
            .collect(partitioningBy(Dish::isVegetarian,
                    counting()));

        System.out.println(result3);
    }

    @Test
    void 소수와비소수() {
        var result = partitionPrimes(100);

        System.out.println(result.get(true));
    }

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
    void collect_종합_223() {
        var result = getMenuStream().collect(Collectors.toSet());
        System.out.println(result);

        var result1 = getMenuStream().collect(toList());
        System.out.println(result1);

        var collect = getMenuStream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(collect);

        var result2 = getMenuStream().map(Dish::getName).collect(joining( ", "));
        System.out.println(result2);

        var result3 = getMenuStream()
            .collect(maxBy(Comparator.comparingInt(Dish::getCalories)));
        System.out.println(result3);

        var result4 = getMenuStream()
                .collect(maxBy(Comparator.comparingInt(Dish::getCalories))).orElseGet(null);
        System.out.println(result4);

        var result5 = getMenuStream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(result5);

        var result6 = getMenuStream()
            .collect(groupingBy(Dish::isVegetarian,
                        groupingBy(Dish::getType
                        , collectingAndThen(toList(), List::size)
                        )
                )
            );

        System.out.println(result6);



    }

    private Stream<Dish> getMenuStream() {
        return Menu.getMenuList().stream();
    }
}
