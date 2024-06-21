package com.example.lambdapattern._04factorybyswitch;

import java.awt.*;

public interface Shape {
    int corners();
    Color color();
    ShapeType type();
}
