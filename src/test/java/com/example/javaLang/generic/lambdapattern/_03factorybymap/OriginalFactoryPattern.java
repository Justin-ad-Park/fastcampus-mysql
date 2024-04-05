package com.example.javaLang.generic.lambdapattern._03factorybymap;

import org.junit.jupiter.api.Test;

public class OriginalFactoryPattern {

    public static Product createProduct(String name) {
        switch(name) {
            case "Car" : return new Car();
            case "Boat" : return new Boat();
            default: throw new RuntimeException("No such product " + name);
        }
    }

    @Test
    void 팩토리메서드_테스트() {
        Product p1 = OriginalFactoryPattern.createProduct("Car");
        Product p2 = OriginalFactoryPattern.createProduct("Boat");
    }

}
