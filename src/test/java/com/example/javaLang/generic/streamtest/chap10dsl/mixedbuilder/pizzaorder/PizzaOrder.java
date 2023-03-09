package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.pizzaorder;


import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class PizzaOrder {
    public static Pizza orderPizza(PizzaType type, ToppingBuilder... toppings) {
        Pizza pizza = new Pizza(type);

        Stream.of(toppings).forEach(getToppingBuilderConsumer(pizza));

        return pizza;
    }

    @NotNull
    private static Consumer<ToppingBuilder> getToppingBuilderConsumer(Pizza pizza) {
        return b -> pizza.addTopping(b.getToppings());
    }

    public static ToppingBuilder topping(Consumer<ToppingBuilder> consumer) {
        ToppingBuilder builder = new ToppingBuilder();

        consumer.accept(builder);

        return builder;
    }

    @Test
    void PizzaTest() {
        Pizza pizza = orderPizza(PizzaType.Cheese,
                topping(t->t.add("올리브").add("토마토")),
                topping(t->t.add("더블크러스트").add("골드포테이트"))
                );

        System.out.println(pizza);
    }
}
