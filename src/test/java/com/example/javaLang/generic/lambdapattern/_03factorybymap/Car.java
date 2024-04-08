package com.example.javaLang.generic.lambdapattern._03factorybymap;

public class Car extends Product {

    public static Car getInstance() {
        return new Car();
    }
}
