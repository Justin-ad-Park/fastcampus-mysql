package com.example.lambdapattern._07CDE;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <Pre>
 * 조건부 연기 로직(Conditional Deferred Execution)
 *
 * 전통적인 메서드 파라미터 자리에 메서드를 넣는 경우 메서드의 실행 결과가 대입되는데 비해
 * 함수형 인터페이스를 파라미터로 넣는 경우에는 내부 로직에서 함수 호출이 된 경우에만 메서드(함수형 인터페이스의 구현체)가 실행된다.
 *
 * 이로 인해 내부 로직에서 호출되지 않을 가능성이 높은 메서드의 경우에
 * 함수형 인터페이스로 메서드를 넘기면 런타임 성능이 개선된다.
 * </Pre>
 */


@SpringBootTest
public class CDETest {

    @Autowired
    CDEexample cdeExample;

    @Test
    void 단순실행테스트() {
        cdeExample.normalMethod();
    }

    @Test
    void 조건부연기실행() {    //conditional deferred execution
        cdeExample.CDEMethod();
    }


}
