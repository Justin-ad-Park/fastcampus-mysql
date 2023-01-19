package com.example.javaLang.generic.functional;

import com.example.javaLang.entity.Apple;
import com.example.javaLang.generic.functional.function.Formatter;

public class AppleFancyFormatter<T extends Apple> implements Formatter<T> {

    @Override
    public String accept(T t) {
        return String.format("Color : %1$s, Weight : %2$dg", t.color().toString(), t.weight());
    }
}
