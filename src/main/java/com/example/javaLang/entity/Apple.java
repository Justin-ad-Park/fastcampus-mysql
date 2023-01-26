package com.example.javaLang.entity;

import lombok.Getter;

@Getter
public class Apple extends Fruit {
    public Apple() {
    }

    public Apple(Integer height) {
        super(height);
    }

    public Apple(Integer height, Integer diameter) {
        super(height, diameter);
    }

    @Override
    public String getName() { return "사과"; }

}