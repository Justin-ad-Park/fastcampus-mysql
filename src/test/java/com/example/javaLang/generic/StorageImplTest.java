package com.example.javaLang.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageImplTest {
    @Test
    public void test() {
        StorageImpl<String> products = new StorageImpl<>(100);

        products.add("Tobu", 1);
        String item = products.get(1);

        Assertions.assertEquals("Tobu", item);
    }

    @Test
    public void testNull() {
        StorageImpl<String> products = new StorageImpl<>(100);
        String item0 = products.get(0);

        Assertions.assertNull(item0);

    }

}