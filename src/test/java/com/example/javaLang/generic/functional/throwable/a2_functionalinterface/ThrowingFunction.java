package com.example.javaLang.generic.functional.throwable.a2_functionalinterface;

import java.util.function.Function;

/**
 * 람다식 또는 스트림에서 try-catch를 사용하지 않고 예외를 던질 수 있는 함수형 인터페이스
 *
 * 스트림 map() 내에 구성되는 try-catch 블럭을 아래 코드와 같이 래핑(wrapping)하여
 * 비즈니스 로직 이외에 구문을 복잡하게 만드는 try-catch 블럭을 제거할 수 있다.
 *
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T,R> {
    R applyThrows(T t) throws Exception;

    @Override
    default R apply(T t) {
        try {
            return applyThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
