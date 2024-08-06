package com.example.javaLang.generic.functional.throwable.d_pipeline;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {
    R applyThrows(T t) throws Exception;

    @Override
    default R apply(T t) {
        try {
            return applyThrows(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    //함수형 메서드 ThrowingFunction을
//    public static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> fn) {
//        return fn::apply;
//    }

}
