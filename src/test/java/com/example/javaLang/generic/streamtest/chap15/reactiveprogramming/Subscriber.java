package com.example.javaLang.generic.streamtest.chap15.reactiveprogramming;

public interface Subscriber<T> {
    void onChange(T t);
}
