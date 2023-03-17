package com.example.javaLang.generic.lambdapattern.chainvalidation;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ProductValidation {
    DEFAULT_PRODUCT("전체 필드 검증", validProductName(), CompareStatus(ProductStatus.SALES), validPrice(1000)),
    EXPENSIVE_PRODUCT("고가 품목 검증", validProductName(), CompareStatus(ProductStatus.SALES), validPrice(10_000)),
    EMPLOYEE_ONLY("임직원 품목 검증", validProductName(), CompareStatus(ProductStatus.SALES), validEmployeeOnly());

    private final Predicate<Product>[] validators;
    private final String desc;

    ProductValidation( String desc,  Predicate<Product>... validators) {
        this.desc = desc;
        this.validators = validators;
    }

    public boolean validate(Product product) {
        return Arrays.stream(validators).allMatch(p -> p.test(product));
    }

    // 상품명 존재 여부
    private static Predicate<Product> validProductName() {
        return p -> !p.productName().isEmpty();
    }

    // 상품 판매 상태
    private static Predicate<Product> CompareStatus(ProductStatus productStatus) {
        return p -> p.productStatus() == productStatus;
    }

    // 상품 가격 지정 금액 이상
    private static Predicate<Product> validPrice(int comparePrice) {
        return p -> p.price() >= comparePrice;
    }

    // 임직원 상품 여부 검증
    private static Predicate<Product> validEmployeeOnly() {
        return p -> p.employeeOnly();
    }

}
