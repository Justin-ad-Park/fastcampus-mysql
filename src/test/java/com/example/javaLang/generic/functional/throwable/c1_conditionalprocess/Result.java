package com.example.javaLang.generic.functional.throwable.c1_conditionalprocess;

import java.util.Optional;
import java.util.function.Function;

/**
 * 성공만, 실패만, 또는 둘 다 처리할 수 있도록 기능이 확장된 Result 클래스
 * @param value
 * @param throwable
 * @param isSuccess
 * @param <V> 결과값을 Generic으로 받음
 * @param <E> 오류 발생 시 발생하는 Throwable을 Generic으로 받음
 */
public record Result<V, E extends Throwable>(V value,
                                             E throwable,
                                             boolean isSuccess) {

    public static <V, E extends Throwable> Result<V,E> success(V value) {
        return new Result(value, null, true);
    }

    public static <V, E extends Throwable> Result<V, E> failure(E throwable) {
        return new Result(null, throwable, false);
    }

    @Override
    public String toString() {
        return "ResultRecord{" +
                "value='" + value + '\'' +
                ", throwable=" + throwable +
                ", isSuccess=" + isSuccess +
                '}';
    }

    // 성공된 결과만 함수 처리하고, 실패 시에는 Optional.empty()를 반환
    public <R> Optional<R> mapSuccess(Function<V, R> fn) {
        return isSuccess ? Optional.ofNullable(value).map(fn) : Optional.empty();
    }

    // 실패된 결과만 처리하고, 성공 시에는 Optional.empty()를 반환
    public <R> Optional<R> mapFailure(Function<E, R> fn) {
        return isSuccess ? Optional.empty() : Optional.ofNullable(throwable).map(fn);
    }

    // 성공과 실패를 각각 처리하는 함수를 받아서 처리
    public <R> R mapSuccessFailure(Function<V, R> successFn, Function<E, R> failureFn) {
        return isSuccess ? successFn.apply(value) : failureFn.apply(throwable);
    }

}
