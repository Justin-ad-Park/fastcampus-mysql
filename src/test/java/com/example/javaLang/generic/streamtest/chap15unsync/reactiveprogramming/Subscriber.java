package com.example.javaLang.generic.streamtest.chap15unsync.reactiveprogramming;

public interface Subscriber<T> {
    void onChange(T t);
}
