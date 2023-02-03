package com.example.javaLang.generic.streamtest;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.groupingBy;

public class Stream168Optional {

    @Test
    void streamOptionalTest() {
        var dish = Menu.getMenu().stream()
                .filter(Dish::isVegetarian)
                .findAny();

    }

    @Test
    void reduceTest() {
        var result = Menu.getMenu().stream()
                .map(Dish::getCalories)
                .reduce(0, (x, y) -> x + y)
                ;

        System.out.println(result);
    }

    @Test
    void reduceByMin() {
        var dishMinCalory = Menu.getMenu().stream()
                .reduce( (a, b) -> a.getCalories() <= b.getCalories() ? a : b )
                ;

        var dishMaxCalory = Menu.getMenu().stream()
                .reduce( (a, b) -> a.getCalories() >= b.getCalories() ? a : b)
                ;

        System.out.println(dishMinCalory + " " + dishMinCalory.get().getCalories());
        System.out.println(dishMaxCalory + " " + dishMaxCalory.get().getCalories());
    }

    @Test
    void page173Quiz() {
        var menuCnt = Menu.getMenu().stream()
                .map(d -> 1)
                .reduce(0, (x, y) -> x + y);

        var cnt = Menu.getMenu().stream().count();

        System.out.println(menuCnt);
        System.out.println(cnt);
    }

    @Test
    void page138Quiz() {
        // 메뉴를 타입별로 매핑한다.
        var resultMap = Menu.getMenu()
                .stream()
                .collect(groupingBy(Dish::getType));

        System.out.println(resultMap);


        // 맵을 다시 하나의 스트림으로 만든다.
        var menus = resultMap.values().stream().flatMap(n -> n.stream());
        menus.forEach(System.out::println);

    }

}
