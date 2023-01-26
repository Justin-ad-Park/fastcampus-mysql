package com.example.javaLang.entity;

import lombok.Getter;

@Getter
public class Orange extends Fruit {
    public Orange() {
    }

    public Orange(Integer height) {
        super(height);
    }

    public Orange(Integer height, Integer diameter) {
        super(height, diameter);
    }

    @Override
    public String getName() { return "오랜지"; }

}