package com.example.javaLang.generic.lambdapattern._10chainvalidation;

public class ProductValidator {
    public boolean valid(Product product, ProductValidation productValidation) {
        return productValidation.validate(product);
    }
}
