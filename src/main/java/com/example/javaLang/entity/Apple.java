package com.example.javaLang.entity;

import com.example.javaLang.generic.functional.function.Formatter;

import java.util.List;

public record Apple(Color color, Integer weight) {
    public static void PrintApple(List<Apple> apples, Formatter<Apple> appleFancyFormatter) {
        for(Apple apple: apples) {
            String output = appleFancyFormatter.accept(apple);
            System.out.println(output);
        }
    }

}

