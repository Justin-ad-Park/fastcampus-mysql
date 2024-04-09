package com.example.lambdapattern._10chainvalidation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChainValidatorTest {

    @Test
    void 상품명존재_validator() {
        ProductValidator productValidator = new ProductValidator();
        Product product = new Product("사과", 1000, ProductStatus.SALES, false);
        boolean resultValid = productValidator.valid(product, ProductValidation.DEFAULT_PRODUCT);

        Assertions.assertTrue(resultValid);
    }

    @Test
    void 상품명Empty_validator() {
        ProductValidator productValidator = new ProductValidator();
        Product product = new Product("", 1000, ProductStatus.SALES, false);
        boolean resultValid = productValidator.valid(product, ProductValidation.DEFAULT_PRODUCT);

        Assertions.assertFalse(resultValid);
    }

    @Test
    void 판매상태_NOT_SALES_validator() {
        ProductValidator productValidator = new ProductValidator();
        Product product = new Product("사과", 1000, ProductStatus.SOLD_OUT, false);
        boolean resultValid = productValidator.valid(product, ProductValidation.DEFAULT_PRODUCT);

        resultValid = productValidator.valid(product, ProductValidation.EXPENSIVE_PRODUCT);

        Assertions.assertFalse(resultValid);
    }

    @Test
    void 가격최저가이내_validator() {
        Product product = new Product("사과", 800, ProductStatus.SALES, false);
        boolean resultValid = ProductValidation.DEFAULT_PRODUCT.validate(product);

        Assertions.assertFalse(resultValid);
    }

    @Test
    void 고가품가격정상_validator() {
        Product product = new Product("사과", 20_000, ProductStatus.SALES, false);
        boolean resultValid = ProductValidation.EXPENSIVE_PRODUCT.validate(product);

        Assertions.assertTrue(resultValid);
    }

    @Test
    void 고가품가격부족_validator() {
        Product product = new Product("사과", 8_000, ProductStatus.SALES, false);
        boolean resultValid = ProductValidation.EXPENSIVE_PRODUCT.validate(product);

        Assertions.assertFalse(resultValid);
    }

    @Test
    void 임직원전용상품_validator() {
        Product product = new Product("임직원 전용 사과", 6_000, ProductStatus.SALES, true);
        boolean resultValid =  ProductValidation.EMPLOYEE_ONLY.validate(product);

        Assertions.assertTrue(resultValid);
    }

}
