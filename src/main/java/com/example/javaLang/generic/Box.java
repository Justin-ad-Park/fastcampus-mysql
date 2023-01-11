package com.example.javaLang.generic;

/***
 * <타입파라미터, ...> 리턴타입 메소드명(매개변수, ...) { ... }
 * 예) public <T> Box<T> boxing(T t) {... }
 *
 * 제네릭 T 가장 단순한 사용 방식
 * @param <T>
 */
public class Box<T> {
    private T t;
    public T get() {return t;}
    public void set(T t) {this.t = t;}
}
