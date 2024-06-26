package com.example.lambdapattern._13decorator;

import com.example.lambdapattern._13decorator.Extras.Milk;
import com.example.lambdapattern._13decorator.Extras.Mocha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class CoffeeOrderTest {
    @Test
    void Test_추가주문객체add방식() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica);
        coffee.addExtraOrder(new Milk());
        coffee.addExtraOrder(new Mocha());

        System.out.println(coffee);
    }

    @Test
    void Test2_추가주문배열방식() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica);
        coffee.addExtraOrder(new Milk(),new Mocha());

        System.out.println(coffee);
    }

    @Test
    void Test3() {
        Coffee coffee = new Coffee(Cup.HOT_SMALL, CoffeeBean.Arabica);
        new Milk().andThen(new Mocha()).add(coffee);

        System.out.println(coffee);
    }

    @Test
    void FunctionalTest() {
        String abc = "abc";

        BiPredicate<String, String> equalsFunction = String::equals;

        Assertions.assertTrue(equalsFunction.test(abc, "abc"));
        /**
         * abc : 첫 번째 파라미터, 호출할 함수가 존재하는 객체
         * "abc" : 두 번째 파라미터 함수(메서드)
         */
    }

    @Test
    void FunctionTest2() {
        ClassA classA = new ClassA();
        BiFunction<String, String, String> addFunction = classA::apply;

        String result = addFunction.apply("abc", "def");

        Assertions.assertEquals("abcdef", result);
    }

    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    public class ClassA {
        public String apply(String a, String b) {
            return a + b;
        }
    }

    @Test
    void FunctionTest3() {

        TriFunction<ClassA, String, String, String> addFunction = ClassA::apply;

        ClassA classA = new ClassA();

        String result = addFunction.apply(classA,"abc", "def");

        Assertions.assertEquals("abcdef", result);
    }


}
