package com.example.javaLang.generic.streamtest.entity;

import java.util.Arrays;
import java.util.List;

public class Menu {

    public static List<Dish> getMenuList()
    {
        return Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("Chicken", false, 400, Dish.Type.MEAT),
            new Dish("French fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("pizza", true, 300, Dish.Type.FISH),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
        );
    }
}
