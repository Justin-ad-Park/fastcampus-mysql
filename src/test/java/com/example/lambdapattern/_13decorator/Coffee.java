package com.example.lambdapattern._13decorator;

import com.example.lambdapattern._13decorator.Extras.ExtraAddition;

import java.util.ArrayList;
import java.util.List;

public final class Coffee {
    private final Cup cup;
    private final CoffeeBean coffeeBean;

    public Coffee(Cup cup, CoffeeBean coffeeBean) {
        this.cup = cup;
        this.coffeeBean = coffeeBean;
    }

    private List<ExtraAddition> extras = new ArrayList<>();

    public void addExtraOrder(ExtraAddition extra) {
        extras.add(extra);
    }

    public void addExtraOrder(ExtraAddition ...extra) {
        extras.addAll(List.of(extra));
    }

    public Cup cup() {
        return cup;
    }

    public CoffeeBean coffeeBean() {
        return coffeeBean;
    }

    @Override
    public String toString() {
        return String.format("Coffee \ncup=%s \ncoffeeBean=%s \nextras=%s", cup, coffeeBean, extras);
    }

}
