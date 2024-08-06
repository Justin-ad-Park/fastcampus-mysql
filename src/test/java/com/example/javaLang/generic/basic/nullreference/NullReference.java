package com.example.javaLang.generic.basic.nullreference;

import org.junit.jupiter.api.Test;

public class NullReference {

    @Test
    void test() {
        String str = null;
        Boolean isString = str instanceof String;

        System.out.println(isString);

        str = "ABC";

        isString = str instanceof String;

        System.out.println(isString);

    }
}
