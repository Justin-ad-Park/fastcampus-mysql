package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

public interface ResultFactory<R, E extends Throwable> {
    R success(String value);
    R failure(E e);
}
