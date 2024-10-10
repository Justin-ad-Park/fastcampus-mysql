package com.example.javaLang.generic.functional.throwable.b2_stepbystep;

/**
 * Record를 사용하지 않고, 동일한 기능을 클래스로 구현한 ClassResult
 * @param <E>
 */
public class ClassResult <E extends Throwable>{
    private final String value;
    private final E throwable;
    private final boolean isSuccess;

    private ClassResult(String value, E throwable, boolean isSuccess) {
        this.value = value;
        this.throwable = throwable;
        this.isSuccess = isSuccess;
    }

    public String getValue() {
        return value;
    }

    public E getThrowable() {
        return throwable;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public static ClassResult success(String value) {
        return new ClassResult(value, null, true);
    }

    public static <E extends Throwable> ClassResult<E> failure(E throwable) {
        return new ClassResult(null, throwable, false);
    }

    @Override
    public java.lang.String toString() {
        return "Result{" +
                "value=" + value +
                ", throwable=" + throwable +
                ", isSuccess=" + isSuccess +
                '}';
    }
}