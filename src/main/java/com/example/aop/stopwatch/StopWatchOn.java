package com.example.aop.stopwatch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 메소드에 적용됨
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지
public @interface StopWatchOn {
    String methodName() default ""; // 메소드 이름을 저장할 속성
}