package com.example.javaLang.generic.streamtest.chap06;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class stream202Collect {
    @Test
    void collectCount() {
        var result = Menu.getMenuList().stream().collect(Collectors.counting());
        System.out.println(result);

        result = Menu.getMenuList().stream().count();
        System.out.println(result);
    }

    @Test
    void collectMax() {

        var result = Menu.getMenuList().stream().collect(maxBy(getComparing()));
        System.out.println(result);

        result = Menu.getMenuList().stream().collect(minBy(getComparing()));
        System.out.println(result);

    }

    private Comparator<Dish> getComparing() {
        return Comparator.comparing(Dish::getCalories);
    }

    @Test
    void collectSumming() {
        var totalCalories = Menu.getMenuList().stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
    }

    @Test
    void collectSummingByType() {
        var dishsByType = Menu.getMenuList().stream().collect(Collectors.groupingBy(Dish::getType));

        dishsByType.forEach( (Dish.Type t, List<Dish> list)
                ->
            System.out.println( t.name() + " " +
                    list.stream().collect(Collectors.summingInt(Dish::getCalories))
            ));

    }

    @Test
    void collect207() {
        var result = Menu.getMenuList().stream()
                .collect(reducing(0,
                        Dish::getCalories,
                        Integer::sum));

        System.out.println(result);
    }

    @Test
    void collect208() {
        var size0 = Menu.getMenuList().stream()
                .collect(reducing(0,
                        e->1,
                        Integer::sum));

        var size1 = Menu.getMenuList().size();

        var size2 = Menu.getMenuList().stream().count();

        Assertions.assertTrue((size0 == size1) && (size0 == size2));
        System.out.println(size0);

    }

    @Test
    void collect208_wildcard() {
        var result = Menu.getMenuList().stream()
                .collect(counting());

        System.out.println(result);

    }

    @NotNull
    private Collector<Dish, ?, Integer> counting() {
        return reducing(0,
                getOne(),
                Integer::sum);
    }

    @NotNull
    private Function<Dish, Integer> getOne() {
        return e -> 1;
    }


}
