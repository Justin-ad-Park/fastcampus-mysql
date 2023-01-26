package com.example.javaLang.lambda;

import com.example.javaLang.entity.Apple;
import com.example.javaLang.entity.Fruit;
import com.example.javaLang.entity.Orange;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 생성자 참조 테스트 코드
 */
public class LambdaCreatorTest2 {

    @Test
    void 생성자참조_쉬운예() {
        Supplier<Apple> appleFactory = Apple::new;
        Apple a1 = appleFactory.get();

        System.out.println(a1);
    }


    // 함수형 인터페이스와 람다 표현식을 활용해서 생성자를 참조하는 방식
    @Test
    void creator() {
        Supplier<Apple> c1 = Apple::new;    //Supplier는 get() 메소드 호출 () -> {T}

        Apple a1 = c1.get();
        System.out.println("기본 생성자 :" + a1);

        Function<Integer, Apple> c2 = Apple::new; //Function<T, R> = (T) -> {R};

        BiFunction<Integer, Integer, Apple> c3 = Apple::new; //BiFunction<T, U, R>은 (T U) -> {R};

        Apple a2 = c2.apply(15);
        System.out.println("인자1 생성자 :" + a2);

        a2 = c2.apply(20);
        System.out.println("인자1 생성자 :" + a2);

        Apple a3 = c3.apply(20, 20);
        System.out.println("인자2 생성자 :" + a3);
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

    /**
     * 생성자 참조를 이용해서 if, switch문 없는 팰토리 패턴 만들기
     */
    static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
    static {
        map.put("apple", Apple::new);
        map.put("orange", Orange::new);

        // 추가로 과일명을 enum으로 변경하면 개발자의 실수를 방지할 수 있음
    }

    private Fruit createFruit(String fruit, Integer weight) {
        return map.get(fruit.toLowerCase())
                .apply(weight);
    }

    @Test
    void 생성자참조를응용한팩토리패턴_테스트() {
        List<Fruit> fruits = new ArrayList<>();

        fruits.add( createFruit("apple", 13) );
        fruits.add( createFruit("Orange", 20) );

        fruits.stream().forEach(System.out::println);
    }
}
