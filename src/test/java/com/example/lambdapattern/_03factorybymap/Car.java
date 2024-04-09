package com.example.lambdapattern._03factorybymap;

public class Car extends Product {

    public static Car getInstance() {
        return new Car();
    }
}
