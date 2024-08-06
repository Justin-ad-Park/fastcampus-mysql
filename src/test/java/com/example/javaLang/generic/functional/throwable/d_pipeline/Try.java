package com.example.javaLang.generic.functional.throwable.d_pipeline;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * 스칼라의 try/success/failure 패턴을 java로 구현
 * @param <T>
 * @param <R>
 */
public class Try<T, R> implements Function<T, Optional<R>> {
    private final Function<T,R> successFn;
    private final Function<RuntimeException, R> failureFn;

    // private 생성자로 외부에서 직접 생성하지 못하도록 제한
    private Try(Function<T, R> successFn,
                Function<RuntimeException, R> failureFn) {
        this.successFn = successFn;
        this.failureFn = failureFn;
    }

    public static <T, R> Try<T, R> of(ThrowingFunction <T, R> fn) {
        Objects.requireNonNull(fn);
        return new Try<>(fn, null);
    }

    public Try<T, R> success(Function<R, R> successFn) {
        Objects.requireNonNull(successFn);

        Function<T, R> composedFn = this.successFn.andThen(successFn);
        return new Try<>(composedFn, failureFn);
    }

    public Try<T, R> failure(Function<RuntimeException, R> failureFn) {
        Objects.requireNonNull(failureFn);
        return new Try<>(successFn, failureFn);
    }

    public Optional<R> apply(T value) {
        try {
            var result = successFn.apply(value);
            return Optional.ofNullable(result);
        } catch (RuntimeException e) {
            if(failureFn == null)
                return Optional.empty();

            return Optional.ofNullable(failureFn.apply(e));
        }
    }

}
