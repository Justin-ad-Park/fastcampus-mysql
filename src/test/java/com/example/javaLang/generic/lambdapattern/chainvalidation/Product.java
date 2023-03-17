package com.example.javaLang.generic.lambdapattern.chainvalidation;

import lombok.Builder;

@Builder
public record Product(String productName,
        int price,
        ProductStatus productStatus,
        boolean employeeOnly){ }
