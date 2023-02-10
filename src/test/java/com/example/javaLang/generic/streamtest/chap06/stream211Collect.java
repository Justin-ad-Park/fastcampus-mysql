package com.example.javaLang.generic.streamtest.chap06;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class stream211Collect {

    enum CaloricLevel {DIET, NORMAL, FAT}

    ;

    @Test
    void grouping_221() {
        Map<CaloricLevel, List<Dish>> dishByCaloricLevel =
            Menu.getMenuList().stream()
            .collect(Collectors.groupingBy( dish ->
                {
                    if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                    if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    return CaloricLevel.FAT;
                }
            ));

        System.out.println(dishByCaloricLevel);
    }

}
