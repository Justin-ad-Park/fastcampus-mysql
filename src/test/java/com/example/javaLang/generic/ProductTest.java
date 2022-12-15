package com.example.javaLang.generic;

import com.example.javaLang.generic.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ProductTest {

    @Test
    public void GenericTest() {
        Product<String, String> car1 = new Product<>();
        car1.setKind("Car");
        car1.setModel("K7");

        Product<Integer, String> car2 = new Product<>();
        car2.setKind(1);
        car2.setModel("Genesis");

        ArrayList<Product<String, String>> productList = new ArrayList<>();
        productList.add(car1);

        //productList.add(car2);    // 타입이 맞지 않아 추가 불가(Generic 자동 검출)
        Assertions.assertEquals("K7", productList.get(0).getModel());
    }







}
