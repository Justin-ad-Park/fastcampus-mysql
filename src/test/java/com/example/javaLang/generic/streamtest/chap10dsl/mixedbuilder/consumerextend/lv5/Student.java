package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5;

public class Student {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}