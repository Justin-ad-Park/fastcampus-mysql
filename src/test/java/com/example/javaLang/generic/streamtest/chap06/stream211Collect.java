package com.example.javaLang.generic.streamtest.chap06;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.stream.Collectors.*;

public class stream211Collect {

    enum CaloricLevel {DIET, NORMAL, FAT};

    @Test
    void 기본그룹핑() {
        Map<Dish.Type, List<Dish>> dishesByType = Menu.getMenuList().stream()
                .collect(groupingBy(Dish::getType));

        System.out.println(dishesByType);
    }

    @Test
    void grouping_211() {
        Map<CaloricLevel, List<Dish>> dishByCaloricLevel =
            Menu.getMenuList().stream()
            .collect(groupingBy(dish ->
                {
                    return getCaloricLevel(dish);
                }
            ));

        System.out.println(dishByCaloricLevel);
    }

    @Test
    void grouping_filtering_212() {
        Map<Dish.Type, List<Dish>> caloricDishesByType =
            Menu.getMenuList().stream()
                .collect(
                    groupingBy(Dish::getType,
                    filtering(dish -> dish.getCalories() > 500, toList())
                    )
                );
        System.out.println(caloricDishesByType);

        Map<Dish.Type, List<String>> dishNamesByType =
            Menu.getMenuList().stream()
                .collect(groupingBy(Dish::getType,
                        mapping(Dish::getName, toList())
                        )
                );

        System.out.println(dishNamesByType);
    }

    @Test
    void 이중그룹핑_214() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
            Menu.getMenuList().stream().collect(
                groupingBy(Dish::getType,
                    groupingBy(this::getCaloricLevel)
                )
            );

        System.out.println(dishesByTypeCaloricLevel);
    }

    @Test
    void 타입별갯수_214() {
        Map<Dish.Type, Long> typesCount =
            Menu.getMenuList().stream()
                .collect(groupingBy(Dish::getType, counting()));

        System.out.println(typesCount);
    }

    @Test
    void 타입별칼로리왕_216() {
        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
            Menu.getMenuList().stream().collect(
                groupingBy(Dish::getType, maxBy(
                    Comparator.comparingInt(Dish::getCalories)
                ))
            );

        System.out.println(mostCaloricByType);

            Map<Dish.Type, Dish> mostCaloricByType2 =
                    Menu.getMenuList().stream().collect(
                        groupingBy(Dish::getType,
                            collectingAndThen(
                                maxBy(Comparator.comparingInt(Dish::getCalories))
                            , Optional::get)
                        )
                    );

            System.out.println(mostCaloricByType2);
    }

    @Test
    void GroupingByAndThenSumming_218() {
        Map<Dish.Type, Integer> totalCaloriesByType =
            Menu.getMenuList().stream().collect(
              groupingBy(Dish::getType, summingInt(Dish::getCalories))
            );

        System.out.println(totalCaloriesByType);
    }

    @Test
    void 그룹핑_칼로리레벨_218() {
        Map<Dish.Type, List<CaloricLevel>> caloricLevelsByType =
                Menu.getMenuList().stream().collect(
                        groupingBy(Dish::getType,
                                mapping(this::getCaloricLevel, toList())
                        )
                );

        System.out.println(caloricLevelsByType);
    }

    @Test
    void 그룹핑_중복제거_218() {
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
            Menu.getMenuList().stream().collect(
                groupingBy(Dish::getType,
                    mapping(this::getCaloricLevel, toSet())
                )
            );

        System.out.println(caloricLevelsByType);
    }

    @Test
    void 그룹핑_셋2_218() {
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
            Menu.getMenuList().stream().collect(
                groupingBy(Dish::getType,
                    mapping(this::getCaloricLevel, toCollection(HashSet::new))
                )
            );

        System.out.println(caloricLevelsByType);
    }



    @NotNull
    private stream211Collect.CaloricLevel getCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) return CaloricLevel.DIET;
        if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
        return CaloricLevel.FAT;
    }

}
