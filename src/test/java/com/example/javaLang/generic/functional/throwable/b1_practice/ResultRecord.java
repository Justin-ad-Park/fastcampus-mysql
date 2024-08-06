package com.example.javaLang.generic.functional.throwable.b1_practice;

/**
 * <Pre>
 * 스캐폴드를 이용하여 정상 처리와 예외 상황을 모두 처리할 수 있는 ResultRecord 레코드를 만들었다.
 * (클래스로도 작성 가능)
 *
 * 스캐폴드 :
 * 소프트웨어 개발에서 스캐폴드(scaffold)는 새로운 프로젝트나 모듈을 생성할 때 필요한 기본 구조와 설정을 자동으로 생성해 주는 도구나 기능을 의미합니다.
 * 이는 특히 웹 애플리케이션 개발에서 많이 사용됩니다.
 *
 * 정의
 * 스캐폴드란 프로그래머가 특정 프레임워크나 언어를 사용할 때 기본적인 파일 구조, 코드, 설정 파일 등을 자동으로 생성 또는 구조화 하는 기능을 말합니다.
 * 이는 개발 초기 단계에서 반복적이고 시간이 많이 걸리는 작업을 자동화하여 개발자가 더 중요한 로직 구현에 집중할 수 있도록 돕습니다.
 *
 * 역할
 * 기본 구조 생성: 프로젝트의 디렉토리 구조와 기본 파일들을 생성해 줍니다. 예를 들어, 모델, 뷰, 컨트롤러 파일이나 설정 파일들을 자동으로 만들어 줍니다.
 * 기본 코드 작성: CRUD(Create, Read, Update, Delete) 기능과 같은 기본적인 코드 스켈레톤을 자동으로 생성해 줍니다.
 * <Font color=blue>생산성 향상: 반복적이고 시간이 많이 걸리는 작업을 자동화(또는 구조화)하여 개발 속도를 높이고 생산성을 향상시킵니다.
 * 일관성 유지: 프로젝트의 코드 스타일과 구조를 일관되게 유지할 수 있도록 도와줍니다. 이는 팀 작업 시 특히 유용합니다.</Font>
 * 학습 도구: 초보 개발자에게는 스캐폴드가 제공하는 기본 구조와 코드를 통해 프레임워크나 언어의 사용법을 쉽게 배울 수 있습니다.
 * </Pre>
 * */

/**
 * ResultRecord 레코드
 *
 * @param value 결과값 - String을 제네릭 V (Value)로 변경하면, 다양한 용도로 사용 가능
 * @param throwable 발생한 예외 정보
 * @param isSuccess 처리 결과
 * @param <E>
 */
public record ResultRecord<E extends Throwable>(String value, E throwable, boolean isSuccess) {

    // 성공된 결과(Result)를 생성할 때 사용하는 정적 팩토리 메서드
    public static <E extends Throwable> ResultRecord<E> success(String value) {
        return new ResultRecord(value, null, true);
    }

    // 예외 발생 시 예외 결과(Result)를 생성할 때 사용하는 정적 팩토리 메서드
    public static <E extends Throwable> ResultRecord<E> failure(E throwable) {
        return new ResultRecord(null, throwable, false);
    }

    @Override
    public String toString() {
        return "ResultRecord{" +
                "value='" + value + '\'' +
                ", throwable=" + throwable +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
