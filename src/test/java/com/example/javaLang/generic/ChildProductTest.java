package com.example.javaLang.generic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChildProductTest {
    @Test
    public void test() {
        ChildProduct<Tv, String, String> product = new ChildProduct<>();

        product.setKind(new Tv());
        product.setCompany("Samsung");
        product.setModel("SmartTV");

        assertEquals("Samsung", product.getCompany());
    }

}