package com.example.javaLang.generic.functional.throwable.c1_conditionalprocess;

import java.util.Optional;
import java.util.function.Function;

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

    public <R> Optional<R> mapSuccess(Function<V, R> fn) {
        return isSuccess ? Optional.ofNullable(this.value).map(fn) : Optional.empty();
    }

    public <R> Optional<R> mapFailure(Function<E, R> fn) {
        return isSuccess ? Optional.empty() : Optional.ofNullable(throwable).map(fn);
    }

    public <R> R map(Function<V, R> successFn, Function<E, R> failureFn) {
        return isSuccess ? successFn.apply(value) : failureFn.apply(throwable);
    }

}
