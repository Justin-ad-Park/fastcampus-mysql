package com.example.javaLang.generic.functional.throwable.a2_functionalinterface;

import java.util.function.Function;

/**
 * 람다식 또는 스트림에서 try-catch를 사용하지 않고 예외를 던질 수 있는 함수형 인터페이스
 *
 * 스트림 map() 내에 구성되는 try-catch 블럭을 아래 코드와 같이 래핑(wrapping)하여
 * 비즈니스 로직 이외에 구문을 복잡하게 만드는 try-catch 블럭을 제거할 수 있다.
 *
 * ThrowingFuncton은 예외가 발생할 수 있는 Function에서 예외처리 코드를 제거하기 위해 반복적으로 사용되는 보일러플레이트이다.
 *
 * 보일러 플레이트 특징
 *  -특정 작업이나 기능을 수행하기 위해 자주 사용되는 코드.
 *  -반복적이고 표준화된 코드 블록.
 *  -개발자가 여러 프로젝트에서 재사용할 수 있는 코드.
 *
 * 유래
 *  반복 인쇄용 금속 철판: 19세기와 20세기 초반에, 신문사들은 기사를 금속판에 새겨서 인쇄하는 방식을 사용했습니다.
 *  이러한 금속판을 "보일러 플레이트"라고 불렀는데, 이는 산업용 보일러를 만드는 데 사용되는 견고한 철판과 유사했기 때문입니다.
 *
 *  소프트웨어 개발에서 보일러플레이트는 반복적으로 작성해야 하는 코드의 조각을 미리 만들어 둔 것 또는 반복적으로 재사용되는 코드 자체를 의미합니다.
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
