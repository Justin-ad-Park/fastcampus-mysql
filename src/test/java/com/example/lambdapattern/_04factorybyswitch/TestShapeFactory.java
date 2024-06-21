package com.example.lambdapattern._04factorybyswitch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

@Slf4j
public class TestShapeFactory {

    @Test
    void test() {
        Shape shape = ShapeType.CIRCLE.createInstance(Color.black);

        Assertions.assertEquals(Color.black, shape.color());
        Assertions.assertEquals(ShapeType.CIRCLE, shape.type());
    }
}
