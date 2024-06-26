package com.example.lambdapattern._13decorator.Extras;

import com.example.lambdapattern._13decorator.Coffee;

import java.util.Objects;

@FunctionalInterface
public interface ExtraOrder {
    Coffee add(Coffee c);

    default ExtraOrder andThen(ExtraOrder after) {
        Objects.requireNonNull(after);
        return (Coffee c) -> after.add(add(c));
    }
}
