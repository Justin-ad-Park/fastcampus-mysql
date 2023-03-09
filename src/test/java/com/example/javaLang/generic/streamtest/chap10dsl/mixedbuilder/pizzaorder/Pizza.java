package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.pizzaorder;


import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private final PizzaType type;
    private List<Topping> toppings = new ArrayList<>();

    public Pizza(final PizzaType type) {
        this.type = type;
    }

    public void addTopping(List<Topping> toppings) {
        this.toppings.addAll(toppings);
    }

    @Override
    public String toString() {
        String result = "Pizza" + this.type.toString();

        result = toppings.stream()
                .map(t -> t.toString())
                .reduce(result, (a, b) -> a + "\n" + b);

        return result;
    }

}