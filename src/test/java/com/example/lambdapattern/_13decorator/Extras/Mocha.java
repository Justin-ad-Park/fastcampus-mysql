package com.example.lambdapattern._13decorator.Extras;

import com.example.lambdapattern._13decorator.Coffee;

public class Mocha extends ExtraAddition implements ExtraOrder {
    @Override
    public Coffee add(Coffee c) {
        c.addExtraOrder(this);
        return c;
    }

    @Override
    public String name() {
        return "Mocha";
    }

    @Override
    public int cost() {
        return 300;
    }
}
