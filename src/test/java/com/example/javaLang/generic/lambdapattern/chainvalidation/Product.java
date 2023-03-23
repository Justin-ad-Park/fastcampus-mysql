package com.example.javaLang.generic.lambdapattern.chainvalidation;

public record Product(String productName,
        int price,
        ProductStatus productStatus,
        boolean employeeOnly){ }
