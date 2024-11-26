package com.example.pmo.shippingpolicy;

public enum ClaimType {
    PARTIAL_CANCELLATION("부분 취소"),
    FULL_CANCELLATION("전체 취소"),
    PARTIAL_RETURN("부분 반품"),
    FULL_RETURN("전체 반품");

    private String name;

    ClaimType(String name) {
        this.name = name;
    }
}

