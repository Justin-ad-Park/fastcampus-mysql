package com.example.javaLang.generic.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListFilter {

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e: list) {
            if(p.test(e)) {
                result.add(e);
            }
        }

        return result;
    }
}
