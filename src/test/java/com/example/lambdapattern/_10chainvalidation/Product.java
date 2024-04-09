package com.example.lambdapattern._10chainvalidation;

public record Product(String productName,
        int price,
        ProductStatus productStatus,
        boolean employeeOnly){ }
