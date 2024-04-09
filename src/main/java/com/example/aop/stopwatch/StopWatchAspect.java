package com.example.aop.stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class StopWatchAspect {

    @Around("@annotation(stopWatchOn)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, StopWatchOn stopWatchOn) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return joinPoint.proceed(); // 메소드 실행
        } finally {
            stopWatch.stop();

            String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
            String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

            // methodName() 값을 사용하여 어노테이션에서 제공된 메소드 이름 로그 출력
            System.out.println(String.format("\n=====[Running time] %s.%s - %d ms  =============\n\n"
                    , className, joinPoint.getSignature().getName(),
                    stopWatch.getTotalTimeMillis())
            );
        }
    }
}