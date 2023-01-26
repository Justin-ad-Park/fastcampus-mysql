package com.example.javaLang.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Fruit {
    protected final int height;
    protected final int diameter;
    protected final int weight;

    protected final static int normalHeight = 10;
    protected final static int normalDiameter = 10;

    public Fruit() {
        this(normalHeight, normalDiameter);
    }

    public Fruit(Integer height) {
        this(height, normalDiameter);
    }

    public Fruit(Integer height, Integer diameter) {
        this.height = height;
        this.diameter = diameter;
        this.weight = this.height * this.diameter;
    }


    public String getName() { return "과일"; }

    @Override
    public String toString() {
        return String.format("%1$s 높이 : %2$d, 지름 : %3$d, 무게 : %4$d", getName(), this.height, this.diameter, this.weight);
    }

}