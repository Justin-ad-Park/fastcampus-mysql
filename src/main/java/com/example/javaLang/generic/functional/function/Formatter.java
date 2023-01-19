package com.example.javaLang.generic.functional.function;

public interface Formatter<T> {
    String accept(T t);
}
