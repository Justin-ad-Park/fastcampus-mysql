package com.example.javaLang.entity;

import com.example.javaLang.generic.functional.function.Formatter;

import java.util.List;

public record ColorApple(Color color, Integer weight) {
    public static void PrintApple(List<ColorApple> colorApples, Formatter<ColorApple> appleFancyFormatter) {
        for(ColorApple colorApple : colorApples) {
            String output = appleFancyFormatter.accept(colorApple);
            System.out.println(output);
        }
    }

}

