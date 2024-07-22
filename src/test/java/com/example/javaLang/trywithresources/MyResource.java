package com.example.javaLang.trywithresources;

public class MyResource implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("Closed MyResource");
        throw new IllegalStateException();
    }

    public void doSomething() {
        System.out.println("Doing something");
    }
}
