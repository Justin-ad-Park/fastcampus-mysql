package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

public record ResultRecord<E extends Throwable>(String value, E throwable, boolean isSuccess) {

    public static <E extends Throwable> ResultRecord<E> success(String value) {
        return new ResultRecord(value, null, true);
    }

    public static <E extends Throwable> ResultRecord<E> failure(E throwable) {
        return new ResultRecord(null, throwable, false);
    }

}
