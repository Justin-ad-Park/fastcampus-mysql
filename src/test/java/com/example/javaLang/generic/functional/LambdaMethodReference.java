package com.example.javaLang.generic.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Java의 메서드 참조 방식 4가지
 * 정적 메서드 참조 : Static method reference
 * 생성자 참조 : constructor reference
 * 바운드 비정적 메서드 참조 : bound non-static method reference
 * 언바운드 비정적 메서드 참조 : unbound non-static method reference
 */
public class LambdaMethodReference {

    @Test
    void staticMethodReference() {
        Function<String, Integer> asLambda = s -> Integer.parseInt(s);
        Function<String, Integer> asRef = Integer::parseInt;

        Integer i = asLambda.apply("10");
        Assertions.assertEquals(10, i);

        i = asRef.apply("11");
        Assertions.assertEquals(11, i);
    }

    @Test
    void constructorReference() {
        Supplier<String> asLambda = () -> new String();
        Supplier<String> asRef = String::new;

        String s = asLambda.get();
        s = "Hello";

        Assertions.assertEquals("Hello", s);

        String s2 = asRef.get();
        s2 = "Hello2";
        Assertions.assertEquals("Hello2", s2);
    }

    @Test
    void boundNonStaticMethodReference() {
        String str = "Hello";
        Function<Integer, String> asLambda = i -> str.substring(i);
        Function<Integer, String> asRef = str::substring;

        String s = asLambda.apply(1);
        Assertions.assertEquals("ello", s);

        s = asRef.apply(2);
        Assertions.assertEquals("llo", s);
    }

    @Test
    void unboundNonStaticMethodReference() {
        BiFunction<String, Integer, String> asLambda = (s, i) -> s.substring(i);
        BiFunction<String, Integer, String> asRef = String::substring;

        String hello = "Hello";

        String s = asLambda.apply(hello, 1);
        Assertions.assertEquals("ello", s);

        s = asRef.apply(hello,2);
        Assertions.assertEquals("llo", s);
    }

    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    public class ClassA {
        public String apply2(String a, String b) {
            return a + b;
        }
    }


    @Test
    void boundNonStaticMethodReference2() {
        ClassA classA = new ClassA();
        BiFunction<String, String, String> addFunction = classA::apply2;

        String result = addFunction.apply("abc", "def");

        Assertions.assertEquals("abcdef", result);
    }

    @Test
    void unboundNonStaticMethodReference2() {
        TriFunction<ClassA, String, String, String> asLambda = (c, s1, s2) -> c.apply2(s1, s2);

        TriFunction<ClassA, String, String, String> asRef = ClassA::apply2;

        ClassA classA = new ClassA();

        String result = asLambda.apply(classA,"abc", "def");
        Assertions.assertEquals("abcdef", result);

        result = asRef.apply(classA,"abc", "def");
        Assertions.assertEquals("abcdef", result);

    }


}
