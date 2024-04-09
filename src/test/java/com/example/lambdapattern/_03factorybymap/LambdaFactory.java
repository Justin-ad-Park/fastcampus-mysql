package com.example.lambdapattern._03factorybymap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LambdaFactory {
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("Car", Car::getInstance);
        map.put("Boat", Boat::new);
    }

    @Test
    void 람다팩토리_테스트() {
        Product p1 = map.get("Car").get();
        Product p2 = map.get("Boat").get();

        Assertions.assertThrows(NullPointerException.class,  () -> {
            Product p3 = map.get("Error").get();
        });
    }

    public static Product createProduct(String name) {
        Supplier<Product> factory = map.get(name);
        if(factory != null) return factory.get();
        throw new IllegalArgumentException("No such product " + name);
    }

    @Test
    void 람다팩토리_테스트2() {
        Product p1 = createProduct("Car");
        Product p2 = createProduct("Boat");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                        Product p3 = createProduct("Error");
                }
            );
    }
}
