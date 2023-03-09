package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.pizzaorder;

import java.util.ArrayList;
import java.util.List;

public class ToppingBuilder {
    private List<Topping> toppings = new ArrayList<>();

    public ToppingBuilder add(String name) {
        Topping topping = new Topping(name);
        toppings.add(topping);

        return this;
    }

    public List<Topping> getToppings() {
        return toppings;
    }
}
