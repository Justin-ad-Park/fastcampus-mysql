package com.example.javaLang.generic.functional.throwable.d_pipeline;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * 스칼라의 try/success/failure 패턴을 java로 구현
 *
 * Try 생성자를 private으로 숨기고
 * of 메서드를 통해 Try 객체를 생성하도록 함
 *
 * of 메서드는 에러 처리를 wrapping 하는 ThrowingFunction을 받아 Try 객체를 생성함
 *
 *  ThrowingFunction은 예외가 발생할 가능성이 있는 함수를 나타내는 함수형 인터페이스로 인자 T를 받아 결과 R을 반환하며 예외를 던질 수 있음
 *
 * success 메서드는 andThen 을 통해서 데이터 처리 후 성공한 경우에 추가 처리할 함수를 선택적으로 주입할 수 있음
 *         ThrowingFunction의 리턴 값 R이 success 메서드에 주입된 함수의 인자로 넘어가기 때문에
 *         success 메서드는 R 타입을 받아 R 타입을 반환하는 함수로 정의됨
 *
 * failure 메서드는 실패한 경우에 실패를 처리할 함수를 선택적으로 주입할 수 있도록 함
 *       failure 메서드는 RuntimeException을 받아 R 타입을 반환하는 메서드 참조
 *
 * @param <T> 입력값
 * @param <R> 결과값
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

        Function<T, R> composedFn = this.successFn.andThen(successFn);  // andThen을 통해 원래의 람다 함수에 합성됨
        return new Try<>(composedFn, failureFn);    // Try의 successFn, failureFn은 final로 선언되어 있어서 새로운 Try 객체를 생성해야 함
    }

    public Try<T, R> failure(Function<RuntimeException, R> failureFn) {
        Objects.requireNonNull(failureFn);
        return new Try<>(successFn, failureFn);
    }

    public Optional<R> apply(T value) {
        try {
            var result = successFn.apply(value);    //of 팩토리 메서드로 인해 successFn은 반드시 존재하게 됨
            return Optional.ofNullable(result);
        } catch (RuntimeException e) {
            if(failureFn == null)   // 실패 처리 함수가 없는 경우 빈 Optional 반환
                return Optional.empty();

            return Optional.ofNullable(failureFn.apply(e)); // 실패 처리 함수가 있는 경우 실패 처리 함수를 통해 결과 반환
        }
    }

}
