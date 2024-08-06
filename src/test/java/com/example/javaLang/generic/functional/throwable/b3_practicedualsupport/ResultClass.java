package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

public class ResultClass<E extends Throwable> {
    private final String value;
    private final E throwable;
    private final boolean isSuccess;

    public ResultClass(String value, E throwable, boolean isSuccess) {
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

    public static <E extends Throwable> ResultClass<E> success(String value) {
        return new ResultClass(value, null, true);
    }

    public static <E extends Throwable> ResultClass<E> failure(E throwable) {
        return new ResultClass(null, throwable, false);
    }

    @Override
    public String toString() {
        return "ResultClass{" +
                "value='" + value + '\'' +
                ", throwable=" + throwable +
                ", isSuccess=" + isSuccess +
                '}';
    }

}
