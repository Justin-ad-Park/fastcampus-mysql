package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

public interface ResultFailureFactory<R, E extends Throwable> {
    R create(E e);
}