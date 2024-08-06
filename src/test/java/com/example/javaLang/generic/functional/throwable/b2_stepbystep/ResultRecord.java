package com.example.javaLang.generic.functional.throwable.b2_stepbystep;

public record ResultRecord<E extends Throwable>(String value, E throwable, boolean isSuccess) {

    public static ResultRecord success(String value) {
        return new ResultRecord<>(value, null, true);
    }

    public static <E extends Throwable> ResultRecord failure(E throwable) {
        return new ResultRecord<>(null, throwable, false);
    }
}
