package com.example.javaLang.lambda;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaTest2 {


    @RequiredArgsConstructor
    @Getter
    class Apple {
        final int height;
        final int diameter;
        final int weight;

        final static int normalHeight = 10;
        final static int normalDiameter = 10;

        public Apple( ) {
            this(normalHeight, normalDiameter);
        }

        public Apple(Integer height) {
            this(height, normalDiameter);
        }

        public Apple(Integer height, Integer diameter) {
            this.height = height;
            this.diameter = diameter;
            this.weight = this.height * this.diameter;
        }

        @Override
        public String toString() {
            return String.format("사과 높이 : %1$d, 지름 : %2$d, 무게 : %3$d", this.height, this.diameter, this.weight);
        }

    }


    // 함수형 인터페이스와 람다 표현식을 활용해서 생성자를 참조하는 방식
    @Test
    void creator() {
        Supplier<Apple> c1 = Apple::new;    //Supplier는 get() 메소드 호출 () -> {T}

        Apple a1 = c1.get();
        System.out.println(a1.weight);

        Function<Integer, Apple> c2 = Apple::new; //Function<T, R> = (T) -> {R};

        BiFunction<Integer, Integer, Apple> c3 = Apple::new; //BiFunction<T, U, R>은 (T U) -> {R};

        Apple a2 = c2.apply(15);
        System.out.println(a2.weight);

        a2 = c2.apply(20);
        System.out.println(a2.weight);

        Apple a3 = c3.apply(20, 20);
        System.out.println(a3.weight);
    }

    List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for(Integer i:list) {
            result.add(f.apply(i));
        }

        return result;
    }

    @Test
    void creatorByList() {
        List<Integer> heights = Arrays.asList(7,11,12,14);

        List<Apple> apples = map(heights, Apple::new);

        apples.stream().forEach(System.out::println);

    }
}
