package com.example.lambdapattern._04factorybyswitch;

import java.awt.*;
import java.util.Objects;
import java.util.function.Function;

public enum ShapeType {
    CIRCLE(Circle::new),
    TRIANGLE(Triangle::new);

    public final Function<Color, Shape> factory;

    ShapeType(Function<Color, Shape> factory) {
        this.factory = factory;
    }

    public Shape createInstance(Color color) {
        Objects.requireNonNull(color);

        return this.factory.apply(color);

    }
}
