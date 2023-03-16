package com.example.javaLang.generic.streamtest.chap15.reactiveprogramming;

public interface Publisher<T> {
    /**
     *
     * @param subscriber
     * 제네릭 T 또는 부모 객체
     */
    void subscribe(Subscriber<? super T> subscriber);
}
