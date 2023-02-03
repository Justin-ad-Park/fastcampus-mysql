package com.example.javaLang.generic.streamtest.chap05;

import com.example.javaLang.generic.streamtest.entity.Dish;
import com.example.javaLang.generic.streamtest.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

public class Stream142pipelineTest {
    @Test
    void streamPipeline() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> threeHighCaloricDishNames =
                Menu.getMenuList().stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .map(Dish::getName)
                .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishNames);

        stopWatch.stop();
        System.out.println("스트림 limit(3).map() 수행 시간 :\n" + stopWatch.prettyPrint());
    }

    @Test
    void streamPipelineWithMapLimit() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> threeHighCaloricDishNames =
                Menu.getMenuList().stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishNames);

        stopWatch.stop();
        System.out.println("스트림 map().limit(3) 수행 시간 :\n" + stopWatch.prettyPrint());
    }

    @Test
    void streamPipelineSout() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> threeHighCaloricDishNames =
                Menu.getMenuList().stream()
                        .filter(dish -> {
                            System.out.println("Filtering:" + dish);
                            return dish.getCalories() > 300;
                        })
                        .limit(3)
                        .map(dish -> {
                            System.out.println("Mapping:" + dish);
                            return dish.getName();
                        })
                        .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishNames);

        stopWatch.stop();
        System.out.println("스트림 수행 시간 :\n" + stopWatch.prettyPrint());
    }

    @Test
    void streamPipelineParallel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> threeHighCaloricDishNames =
                Menu.getMenuList().parallelStream()
                        .filter(dish -> {
                            System.out.println("Filtering:" + dish);
                            return dish.getCalories() > 300;
                        })
                        .limit(3)
                        .map(dish -> {
                            System.out.println("Mapping:" + dish);
                            return dish.getName();
                        })
                        .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishNames);

        stopWatch.stop();
        System.out.println("병렬스트림 수행 시간 :\n" + stopWatch.prettyPrint());
    }

}
