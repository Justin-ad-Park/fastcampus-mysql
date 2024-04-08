package com.example.reactiveprogramming.chapter08.backpressurebuffer;

import reactor.core.publisher.Hooks;

/**
 * <pre>
 *     이 코드는 다음과 같이 작동합니다:
 *
 * Hooks.onOperatorDebug()를 호출하여 디버깅을 위한 어셈블리 정보를 스택 트레이스에 추가합니다.
 * Hooks.onNextDropped()와 Hooks.onOperatorError()를 사용하여 드롭된 이벤트와 연산자 에러를 처리하는 로직을 정의합니다.
 * Hooks.enableContextLossTracking()를 호출하여 컨텍스트 손실 추적을 활성화합니다. 이는 리액티브 스트림에서 Context가 예상대로 전달되지 않을 때 경고하거나 에러를 발생시키는 데 사용됩니다.
 * 마지막으로, try-finally 블록을 사용하여 애플리케이션이 종료되기 전에 Hooks 설정을 리셋합니다. 이렇게 하여 다른 부분에 영향을 주지 않도록 합니다.
 * 이 수정된 예시는 Hooks의 사용법을 올바르게 보여주며, addAssemblyInfo() 메서드의 직접적인 사용 없이도 유사한 결과를 얻을 수 있습니다.
 * </pre>
 */
public class ReactorContextLossExample {
    public static void main(String[] args) {
        // Operator Debugging 활성화 - 스택 트레이스에 어셈블리 정보 추가
        Hooks.onOperatorDebug();

        // onNext 이벤트가 드롭될 때의 처리를 정의
        Hooks.onNextDropped(System.out::println);

        // 연산자 에러가 발생했을 때의 처리를 정의
        Hooks.onOperatorError((error, data) -> new RuntimeException("Operator error", error));

        // Context Loss Detection 활성화
        Hooks.enableContextLossTracking();

        // 여기에 Context를 사용하는 리액티브 스트림 코드 추가

        try {
            // Context를 사용하는 리액티브 스트림의 로직을 실행
        } finally {
            // 애플리케이션이 종료되기 전에 모든 Hooks 설정 리셋
            Hooks.resetOnEachOperator();
            Hooks.resetOnOperatorDebug();
            Hooks.disableContextLossTracking();
        }
    }
}

